package com.silang.javalib;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestgetGenericSuperclass<T> {

    public TestgetGenericSuperclass() {
        System.out.println(getClass().getGenericSuperclass());
    }

    public static void main(String[] args) {

        Student student = new Student();
        // getClass() 获得该类的类类型(即类型变量)
        Class clazz = student.getClass();
        // getSuperclass() 获得该类的父类
        System.out.println(clazz.getSuperclass());

        // getGenericSuperclass() 获得该类带有泛型的父类
        Type type = clazz.getGenericSuperclass();
        System.out.println(type);
        // Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。

        // ParameterizedType 参数化类型，即泛型
        // 将Type转化为参数化类型(即泛型)
        ParameterizedType p = (ParameterizedType) type;

        // getActualTypeArguments() 获取参数化类型的数组，泛型可能有多个
        Type[] actualTypeArguments = p.getActualTypeArguments();

        // 将Type转化为类型变量(即Class)
        Class c1 = (Class) actualTypeArguments[0];
        Class c2 = (Class) actualTypeArguments[1];
        System.out.println(c1);
        System.out.println(c2);
    }

}


class Person<T1, T2> {

}

class Student extends Person<Integer, String> {


}