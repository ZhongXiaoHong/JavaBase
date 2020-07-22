package com.silang.javalib.hand_write_simple_retrofit.custom;

public interface MyCall<T> {


    void enqueue(MyCallback<T> callback);
}
