package com.silang.javalib;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Response<T> {
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

    public static void main(String[] args) {


        Gson gson = new Gson();
        Response<Data> response = new Response<>(200, "OK", new Data(100, "zxh"));
        String json = gson.toJson(response);
        System.out.println(json);

        //class com.silang.javalib.TypeRef   class java.lang.Object

//class com.silang.javalib.Response$1   com.silang.javalib.TypeRef<com.silang.javalib.Response<com.silang.javalib.Data>>
       // Type type = new TypeRef<Response<Data>>(){}.getType();

        //com.silang.javalib.TypeRef<T>
       // Type type = new TyperRefSon<Response<Data>>(){}.getType();
      //  Type type = new TyperRefSon2().getType();

        Type type = new TypeToken<Response<Data>>(){}.getType();
        Response<Data> response2 = gson.fromJson(json, type);
        System.out.println(response2.data);


        gson.getClass().getSuperclass();
        gson.getClass().getGenericSuperclass();

    }
}

class TyperRefSon2 extends TypeRef<Response<Data>> {

}

class TyperRefSon<T> extends TypeRef<T> {

}

class TypeRef<T> {
    Type mType;

    T t;

    TypeRef() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        mType = types[0];

    }

    public Type getType() {
        return mType;
    }
}

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
