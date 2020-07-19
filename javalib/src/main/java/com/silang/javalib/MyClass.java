package com.silang.javalib;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.List;

public class MyClass<T> {

    List<String>[] listArray;
    T[] t;

    public static void main(String[] args) throws Exception {

        Field listArrayField = MyClass.class.getDeclaredField("listArray");
        Type listArrayType = listArrayField.getGenericType();
        System.out.println(listArrayType.getClass().getTypeName());


        Field tField = MyClass.class.getDeclaredField("t");
        Type tType = tField.getGenericType();
        System.out.println(tType.getClass().getTypeName());

    }

}