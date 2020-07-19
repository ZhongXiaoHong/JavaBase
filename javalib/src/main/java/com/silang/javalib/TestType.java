package com.silang.javalib;

import java.io.Serializable;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public class TestType<K extends Comparable & Serializable, V, Q> {

    K key;
    V value;
    //    String age;
    Map<String, Integer> map = new HashMap<>();
    //    Map.Entry<Double,String> entry;
    Map<K, V> kvMap;

    public static void main(String[] args) throws Exception {

//        Field mapField = TestType.class.getDeclaredField("map");
//        ParameterizedType pt = (ParameterizedType)mapField.getGenericType();
////        System.out.println(pt.getRawType());//TODO interface java.util.Map
////        System.out.println(pt.getOwnerType());//TODO  null  Map本身不是谁的内部类所以没有拥有者
//        System.out.println("****************************");
//        for (Type actualTypeArgument : pt.getActualTypeArguments()) {
//            System.out.println(actualTypeArgument.getClass().getName());
//            System.out.println(actualTypeArgument);//TODO class java.lang.String  class java.lang.Integer
//        }
//        System.out.println("****************************");
//
////        Field entryField  = TestType.class.getDeclaredField("entry");
////        ParameterizedType pt2 = (ParameterizedType)entryField.getGenericType();
////        System.out.println(pt2.getOwnerType());//TODO interface java.util.Map  Entry是Map的内部类所以拥有者是Map
//
//        Field kvMapField = TestType.class.getDeclaredField("kvMap");
//        ParameterizedType kvType = (ParameterizedType)kvMapField.getGenericType();
//        System.out.println("############################");
//        for (Type actualTypeArgument : kvType.getActualTypeArguments()) {
//            System.out.println(actualTypeArgument.getClass().getName());
//            System.out.println(actualTypeArgument);//TODO class java.lang.String  class java.lang.Integer
//        }
//        System.out.println("############################");
////        for (TypeVariable<Class<TestType>> typeParameter : TestType.class.getTypeParameters()) {
////            System.out.println(typeParameter);
////        }

        Field keyField = TestType.class.getDeclaredField("key");
        TypeVariable keytv = (TypeVariable) keyField.getGenericType();
        for (Type bound : keytv.getBounds()) {
            System.out.println(bound);
            //TODO interface java.lang.Comparable
            //TODO interface java.io.Serializable
        }

        System.out.println(keytv.getGenericDeclaration());//TODO class com.silang.javalib.TestType

        System.out.println(keytv.getName());//TODO K


    }
}
