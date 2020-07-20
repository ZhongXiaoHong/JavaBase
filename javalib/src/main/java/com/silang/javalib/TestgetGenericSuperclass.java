package com.silang.javalib;

public class TestgetGenericSuperclass<T> {

    public TestgetGenericSuperclass() {
        System.out.println(getClass().getGenericSuperclass());
    }

    public static void main(String[] args) {

        new TestgetGenericSuperclass<String>();
        new TestgetGenericSuperclass<Integer>();
    }

    //getclass
    //getGenericSuperclass
}
