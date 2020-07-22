package com.silang.javalib.hand_write_simple_retrofit.custom;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleRetrofit {

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();


    class Builder {

    }

    public <T> T create(Class<T> service) {


        if (service == null || !service.isInterface()) {//不是接口类型  或者null
            throw new RuntimeException("null 或者  不是接口  class");
        }

        Class<?>[] clazzArray = {service};

        //ClassLoader var0, Class<?>[] var1, InvocationHandler var2
        return (T) Proxy.newProxyInstance(service.getClassLoader(), clazzArray, new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        return doRequst(o, method, objects);
                    }
                }

        );


    }

    private Object doRequst(Object rpoxy, Method method, Object[] objects) throws IllegalAccessException, InstantiationException {

        MyCallImpl myCallInstance = new MyCallImpl();
        try {

            String path = "";
            Annotation[] annos = method.getAnnotations();
            //TODO 为什么是二维数组  因为有多个参数   每个参数又可能有多个注解
            //   Annotation[][] paramAnnos = method.getParameterAnnotations();
            Parameter[] parameters = method.getParameters();
            for (Annotation anno : annos) {
                if (anno instanceof MyGet) {
                    String myGet = ((MyGet) anno).value();
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        Annotation[] paramAnnos = parameter.getAnnotations();
                        for (Annotation paramAnno : paramAnnos) {
                            if (paramAnno instanceof MyPath) {
                                MyPath pathAnno = (MyPath) paramAnno;
                                String pathKey = pathAnno.value();
                                myGet = myGet.replace("{" + pathKey + "}", objects[i].toString());
                                path = myGet;
                            }
                        }

                    }

                }
            }

            Request request = new Request.Builder()
                    .url("https://api.github.com/" + path)
                    .build();
            myCallInstance.request = request;
            myCallInstance.method = method;


        } catch (Exception e) {
            e.printStackTrace();
        }

        ParameterizedType pt = ((ParameterizedType) method.getGenericReturnType());


        return myCallInstance;
    }


    class MyCallImpl<T> implements MyCall<T> {
        Request request;
        Method method;


        @Override
        public void enqueue(MyCallback<T> mycallback) {

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    System.out.println("***********");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    String json = response.body().string();
                    ParameterizedType pt = (ParameterizedType) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

                    Object obj = gson.fromJson(json, pt);

                    mycallback.onResonse((T) obj);

                    System.out.println(obj);

                }
            });

        }
    }
}
