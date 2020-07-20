package com.silang.javalib;

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
