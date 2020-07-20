package com.silang.javalib;



import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

public class TypeTest<T, V extends  @Custom Number & Serializable> {
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

    public static void main(String[] args) throws Exception {
       // test();
       // test2();
        test3();
        //test4();
//        Field tClass = TypeTest.class.getField("testClass");
//        System.out.println("Class1:" + tClass.getGenericType());//获取泛型类型，由于我们这个属性声明时候没有使用泛型，所以会获得原始类型
//        Field tClass2 = TypeTest.class.getField("testClass2");
//        System.out.println("Class2:" + tClass2.getGenericType());//获取泛型类型

    }

    private static void test4() throws NoSuchFieldException {
        Field mapWithWildcard = TypeTest.class.getField("mapWithWildcard");
        Type wild = mapWithWildcard.getGenericType();//先获取属性的泛型类型 Map<? super String, ? extends Number>
        if (wild instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) wild;
            Type[] actualTypes = pType.getActualTypeArguments();//获取<>里面的参数变量 ? super String, ? extends Number
            System.out.println("WildcardType1:" + Arrays.asList(actualTypes));
            WildcardType first = (WildcardType) actualTypes[0];//? super java.lang.String
            WildcardType second = (WildcardType) actualTypes[1];//? extends java.lang.Number
            System.out.println("WildcardType2: lower:" + Arrays.asList(first.getLowerBounds()) + "  upper:" + Arrays.asList(first.getUpperBounds()));//WildcardType2: lower:[class java.lang.String]  upper:[class java.lang.Object]
            System.out.println("WildcardType3: lower:" + Arrays.asList(second.getLowerBounds()) + "  upper:" + Arrays.asList(second.getUpperBounds()));//WildcardType3: lower:[]  upper:[class java.lang.Number]
        }
    }

    private static void test3() throws NoSuchFieldException {
        Field tArray = TypeTest.class.getField("tArray");
        System.out.println("数组参数类型1:" + tArray.getGenericType());
        Field ltArray = TypeTest.class.getField("ltArray");
        System.out.println("数组参数类型2:" + ltArray.getGenericType());//数组参数类型2:java.util.List<T>[]
        if (tArray.getGenericType() instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) tArray.getGenericType();
            System.out.println("数组参数类型3:" + arrayType.getGenericComponentType());//数组参数类型3:T
            System.out.println(arrayType.getGenericComponentType() instanceof TypeVariable);
        }

        if (ltArray.getGenericType() instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) ltArray.getGenericType();
            System.out.println("数组参数类型4:" + arrayType.getGenericComponentType());//数组参数类型4:List<T>
            System.out.println(arrayType.getGenericComponentType() instanceof  ParameterizedType);
        }
    }

    private static void test2() throws NoSuchFieldException {
        Field list = TypeTest.class.getField("list");
        Type genericType1 = list.getGenericType();
        System.out.println("参数类型11:" + (genericType1 instanceof ParameterizedType)); //参数类型1:java.util.List<T>
        System.out.println("参数类型1:" + genericType1.getTypeName()); //参数类型1:java.util.List<T>

        Field map = TypeTest.class.getField("map");
        Type genericType2 = map.getGenericType();
        System.out.println("参数类型2:" + genericType2.getTypeName());//参数类型2:java.util.Map<java.lang.String, T>

        if (genericType2 instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType2;
            Type[] types = pType.getActualTypeArguments();
            System.out.println("--------"+(types[0] instanceof Class));
            System.out.println("参数类型列表:" + Arrays.asList(types));//参数类型列表:[class java.lang.String, T]
            System.out.println("参数原始类型:" + pType.getRawType());//参数原始类型:interface java.util.Map
            System.out.println("参数父类类型:" + pType.getOwnerType());//参数父类类型:null,因为Map没有外部类，所以为null
        }
    }

    private static void test() throws NoSuchFieldException {
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


        System.out.println((typeVariable.getGenericDeclaration() == TypeTest.class));

        new Test("abc");
        new Test(123);
    }


}

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ANNOTATION_TYPE, CONSTRUCTOR, FIELD,
        METHOD, PACKAGE, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE})
@interface Custom {
}


 class Test<T> {
    public <U> Test(U k) {
        System.out.println("*********************"+k);
    }
}