package com.silang.javalib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestgetDeclaredField extends A {

    private String spa = "子私有";

    public String spu = "子公有";


    public static void main(String[] args) throws Exception {
        TestgetDeclaredField mTestgetDeclaredField = new TestgetDeclaredField();
        for (Field field : mTestgetDeclaredField.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.get(mTestgetDeclaredField));

        }

        System.out.println("**********************************************************");

        for (Field field : mTestgetDeclaredField.getClass().getFields()) {
            field.setAccessible(true);
            System.out.println(field.get(mTestgetDeclaredField));

        }



    }
}


class A {

    private String ppa = "父私有";

    public String ppu = "父公有";


}
