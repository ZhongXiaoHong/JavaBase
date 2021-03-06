### 反射的Type体系

> 反射与泛型的Type体系

对一个泛型类进行反射获取泛型中的真实的数据类型，此时需要通过Type体系来完成

![](image/7192354.png)

所以Type的孩子有4个接口+1个实现类



> 全文Demo例子

```java
public class TypeTest<T, V extends @Custom Number & Serializable> {
    private Number number;
    public T t;
    public V v;
    public List<T> list = new ArrayList<>();
    public Map<String, T> map = new HashMap<>();

    public T[] tArray;
    public List<T>[] ltArray;

    public TypeTest testClass;
    public TypeTest<T, Integer> testClass2;

    public Map<? super String, ? extends Number> mapWithWildcard;

    //泛型构造函数,泛型参数为X
    public <X extends Number> TypeTest(X x, T t) {
        number = x;
        this.t = t;
    }

    //泛型方法，泛型参数为Y
    public <Y extends T> void method(Y y) {
        t = y;
    }
}
```

**自定义注解Custom**

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ANNOTATION_TYPE, CONSTRUCTOR, FIELD,
        METHOD, PACKAGE, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE})
public @interface Custom {
}
```



> TypeVariable

类型标量，例如List<T>中的T,Map<K,V>中的K，V

TypeVariable的源码

```java
interface TypeVariable<D extends GenericDeclaration> extends Type, AnnotatedElement {
	//返回类型参数的上界，如果没有上界就返回Object
    //例如class TypeTest<T, V extends @Custom Number & Serializable>
    //V的上界有两个Number和Serializable
    Type[] getBounds();
    
    //类型参数声明的载体
    //class TypeTest<T, V extends @Custom Number & Serializable>
    //中V的载体就是TypeTest
    //getGenericDeclaration 返回的是GenericDeclaration类型
    //GenericDeclaration的实现类只有三种，Contruct、Method、Class
    //表示泛型可以声明的地方只有这三种
    D getGenericDeclaration();
   
    //TODO Java 1.8加入 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，
    //TODO 我们可以通过它拿到相应的注解
     AnnotatedType[] getAnnotatedBounds();

}
```

**GenericDeclaration的源码**

```java
 public interface GenericDeclaration extends AnnotatedElement {     
    public TypeVariable<?>[] getTypeParameters();
}
```

GenericDeclaration接口有三个实现类分别是Method、Constructor、Class

这表示的是只能在类（接口）、方法、构造函数这三处地方定义反省参数，其他地方只是在使用

```java
//TODO 在类上定义泛型变量 T
public class TestGenericDeclaration<T> {
    
    
    //TODO 使用泛型变量  T
    T t;
    
    //TODO 在构造函数上定义泛型变量 U
    public <U> TestGenericDeclaration(U u){
        
    }

    //TODO 在方法上定义泛型变量 U
    public <W> void dosth(W w){
        
    }
}
```



测试：

```java
Field v = TypeTest.class.getField("v");//用反射的方式获取属性 public V v; 
TypeVariable typeVariable = (TypeVariable) v.getGenericType();//获取属性类型
System.out.println("TypeVariable1:" + typeVariable);
System.out.println("TypeVariable2:" + Arrays.asList(typeVariable.getBounds()));//获取类型变量上界
System.out.println("TypeVariable3:" + typeVariable.getGenericDeclaration());//获取类型变量声明载体
//1.8 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，我们可以通过它拿到相应的注解
AnnotatedType[] annotatedTypes = typeVariable.getAnnotatedBounds();        
System.out.println("TypeVariable4:" + Arrays.asList(annotatedTypes) + " : " +
                                                                    Arrays.asList(annotatedTypes[0].getAnnotations()));
System.out.println("TypeVariable5:" + typeVariable.getName());
```

输出结果：

```text
TypeVariable1:V
TypeVariable2:[class java.lang.Number, interface java.io.Serializable]
TypeVariable3:class typeInfo.TypeTest
TypeVariable4:[sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@511d50c0, sun.reflect.annotation.AnnotatedTypeFactory$AnnotatedTypeBaseImpl@60e53b93] : [@typeInfo.Custom()]
TypeVariable5:V
```





> ParameterizedType 参数化类型

参数化类型，也就是说带<>的类型，如Map<String,Integer>,ParameterizedType对应的是Map<String,Integer>这个整体，

源码如下：

```java
interface ParameterizedType extends Type {
    //获取<>中的类型，如Map<K,V>则得到[K,V]数组
    //此时K,V是一个TypeVaribale类型
    //而对于Map<String,Integer> 得到的是[String.class,Integer.class]
    //此时数组是Class数组，关键是要理解Type的4个子接口+1个Class子类
	Type[] getActualTypeArguments(); 
    //获得<>前面的值，如Map<K,V>则得到Map
    Type getRawType();
    //获取外部类类型，如Map有一个内部类Entry
    //如Map.Entry<K,V>则得到Map
    Type getOwnerType();
}
```

测试：

```java
Field list = TypeTest.class.getField("list");
Type genericType1 = list.getGenericType();
System.out.println("参数类型1:" + genericType1.getTypeName()); //参数类型1:java.util.List<T>

Field map = TypeTest.class.getField("map");
Type genericType2 = map.getGenericType();
System.out.println("参数类型2:" + genericType2.getTypeName());//参数类型2:java.util.Map<java.lang.String, T>

if (genericType2 instanceof ParameterizedType) {
     ParameterizedType pType = (ParameterizedType) genericType2;
     Type[] types = pType.getActualTypeArguments();
     System.out.println("参数类型列表:" + Arrays.asList(types));//参数类型列表:[class java.lang.String, T]
     System.out.println("参数原始类型:" + pType.getRawType());//参数原始类型:interface java.util.Map
     System.out.println("参数父类类型:" + pType.getOwnerType());//参数父类类型:null,因为Map没有外部类，所以为null
}
```

输出：

```
参数类型1:java.util.List<T>
参数类型2:java.util.Map<java.lang.String, T>
参数类型列表:[class java.lang.String, T]
参数原始类型:interface java.util.Map
参数父类类型:null
```



> GenericArrayType 泛型数组

泛型数组类型，比如List<T>[]、List<String>[]、T[]

接口源码：

```java
public interface GenericArrayType extends Type {
    //获取泛型类型数组的声明类型，即获取数组方最后一个括号 [] 前面的部分
    //例如T[]则可以得到T
    //T[][] 则得到的是T[]
    Type getGenericComponentType();
}
```

测试：

```java
 Field tArray = TypeTest.class.getField("tArray");
 System.out.println("数组参数类型1:" + tArray.getGenericType());
 Field ltArray = TypeTest.class.getField("ltArray");
 System.out.println("数组参数类型2:" + ltArray.getGenericType());//数组参数类型2:java.util.List<T>[]
 if (tArray.getGenericType() instanceof GenericArrayType) {
     GenericArrayType arrayType = (GenericArrayType) tArray.getGenericType();
     System.out.println("数组参数类型3:" + arrayType.getGenericComponentType());//数组参数类型3:T
 }
```

输出

```
数组参数类型1:T[]
数组参数类型2:java.util.List<T>[]
数组参数类型3:T
```

个人理解：

List<T>[] 整体对应着GenericArrayType ，List<T>[] 数组每个元素有事List<T>，对应着ParameterizedType

List<String>[] 整体对应着GenericArrayType ，List<String>[] 数组每个元素都是List<String>,对应着ParameterizedType

T[]整体对应着GenericArrayType ，T[]数组每个元素又是T,对应着TypeVariable



> WildcardType 通配符类型

表示通配符类型，即带有？的泛型参数，比如 ？  extends  Number中的？ 和？  super   Number中的？

还有List<?>中的？

源码：

```java
public interface WildcardType extends Type {
   // 获取上界
    Type[] getUpperBounds();
    //获取下界
    Type[] getLowerBounds();
}
```

测试：

```java
//***************************WildcardType*********************************
 Field mapWithWildcard = TypeTest.class.getField("mapWithWildcard");
//先获取属性的泛型类型 Map<? super String, ? extends Number>
//先拿到的是尖括号，是参数化类型
 Type wild = mapWithWildcard.getGenericType();
 if (wild instanceof ParameterizedType) {
     ParameterizedType pType = (ParameterizedType) wild;
     //获取<>里面的参数变量 ? super String, ? extends Number
     //尖括号里面是通配符类型
     Type[] actualTypes = pType.getActualTypeArguments();
     System.out.println("WildcardType1:" + Arrays.asList(actualTypes));
     WildcardType first = (WildcardType) actualTypes[0];//? super java.lang.String
     WildcardType second = (WildcardType) actualTypes[1];//? extends java.lang.Number
     System.out.println("WildcardType2: lower:" + 		Arrays.asList(first.getLowerBounds()) + "  upper:" + Arrays.asList(first.getUpperBounds()));//WildcardType2: lower:[class java.lang.String]  upper:[class java.lang.Object]
     System.out.println("WildcardType3: lower:" + Arrays.asList(second.getLowerBounds()) + "  upper:" + Arrays.asList(second.getUpperBounds()));//WildcardType3: lower:[]  upper:[class java.lang.Number]
 }
```

输出

```
WildcardType1:[? super java.lang.String, ? extends java.lang.Number]
WildcardType2: lower:[class java.lang.String]  upper:[class java.lang.Object]
WildcardType3: lower:[]  upper:[class java.lang.Number]
```



> Class

class 是Type的一个实现类，每一个类在虚拟机中都对应一个Calss 对象,我们可以用在运行时从这个Class对象中获取到类型所有信息。



测试：

```java
 Field tClass = TypeTest.class.getField("testClass");
 System.out.println("Class1:" + tClass.getGenericType());//获取泛型类型，由于我们这个属性声明时候没有使用泛型，所以会获得原始类型
 Field tClass2 = TypeTest.class.getField("testClass2");
 System.out.println("Class2:" + tClass2.getGenericType());//获取泛型类型
```

输出

```
Class1:class typeInfo.TypeTest
Class2:typeInfo.TypeTest<T, java.lang.Int
```



