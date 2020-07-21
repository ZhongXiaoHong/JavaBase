### 分析Gson解析泛型



> getGenericSuperclass()用法

getGenericSuperClass()获取带有泛型的父类

**例子：**

```java
public class Person<T1, T2> {

}
```

```java
public class Student extends Person<Integer, String> {
    
  
	Student student = new Student();
    Class clazz = student.getClass();
    //获取Student的父类Person,不带泛型的
    System.out.println(clazz.getSuperclass());
    
    //获取Person<Integer, String>对应的ParameterizedType 类型
    //怎么去理解？getclass获取的是Person.class
    //那么getGenericSuperclass获取的是 Person<Integer, String> class类型？
    //java这样的类型是用Type表示  这种是属于ParameterizedType
     Type type = clazz.getGenericSuperclass();
     ParameterizedType p = (ParameterizedType) type;
    System.out.println(p);
    
}
```

输出结果

```javascript
class com.itheima.mytest.Person
com.itheima.mytest.Person<java.lang.Integer, java.lang.String>
```

总结：从设计Type体系去理解getSuperGenericClass，结合本例,getClass()返回的是Student.class,而getSuperClass()返回的是Person.class，那么getSuperGenericClass()返回的是Person<Integer,String>.class类型吗？显然不是，java是没有这个类型，为了运行是获取类似这种泛型信息，设计了Type,Person<Integer,String>对应的是ParameterizedType



> Gson解析泛型数据

```java
class Response<T> {
    int code;
    String message;
    T data;

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
```

```java
class Data {
    int total;
    String name;

    public Data(int total, String name) {
        this.total = total;
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data{" +
                "total=" + total +
                ", name='" + name + '\'' +
                '}';
    }
}
```

```java
public static void main(String[] args) {

 		Gson gson = new Gson();
        Response<Data> response = new Response<>(200, "OK", new Data(100, "zxh"));
        String json = gson.toJson(response);
        System.out.println(json);
    
    	//TODO 获取一个Type,注意这里使用的是{}
    	//也就是TypeToken的匿名类来创建对象
    	Type type = new TypeToken<Response<Data>>(){}.getType();
        Response<Data> response2 = gson.fromJson(json, type);
        System.out.println(response2.data);
    	

}
```

上面注释处需要注意的有

1.Type  是通过TypeToken的匿名类创建对象来获取的，为什么要匿名类？去掉{}可以吗？

2.gson.from内部是怎么调用的



【type获取】

```java
 protected TypeToken() {
    this.type = getSuperclassTypeParameter(getClass());
    this.rawType = (Class<? super T>) $Gson$Types.getRawType(type);
    this.hashCode = type.hashCode();
  }
```

```java
  static Type getSuperclassTypeParameter(Class<?> subclass) {
      //这里是关键
    Type superclass = subclass.getGenericSuperclass();
    if (superclass instanceof Class) {
      throw new RuntimeException("Missing type parameter.");
    }
    ParameterizedType parameterized = (ParameterizedType) superclass;
    return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
  }
```

getSuperclassTypeParameter中可以看到调用了subclass.getGenericSuperclass();

这里的subclass实际上是TypeToken

值得注意此时方法调用时实际上并非是TypeToken类的对象

而是TypeToken类的匿名内部类TypeToken<Response<Data>>(){}，

对于匿名内部类TypeToken<Response<Data>>(){}编译时期会生成一个类如下：

```
import com.google.gson.reflect.TypeToken;

final class Response$1 extends TypeToken<Response<Data>> {
    Response$1() {
    }
}
```

所以

Type type = new TypeToken<Response<Data>>(){}.getType();
Response<Data> response2 = gson.fromJson(json, type);

在编译后实际上是

```
Type type = new Response$1().getType();
 Response<Data> response2 = gson.fromJson(json, type);
```

所以 Type superclass = subclass.getGenericSuperclass();这时获取的是ParameterizedType，对应着

Response<Data>泛型信息，所以这里的要是用匿名内部类。