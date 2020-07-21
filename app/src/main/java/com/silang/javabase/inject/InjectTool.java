package com.silang.javabase.inject;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

public class InjectTool {

    public static void register(Activity activity){


        Field[] fields = activity.getClass().getDeclaredFields();

        for (Field field : fields) {
            //TODO 判断Field上是否使用了Inject注解
            if(field.isAnnotationPresent(Inject.class)){
                Inject anno = field.getAnnotation(Inject.class);
                int id = anno.id();
                View view = activity.findViewById(id);
                field.setAccessible(true);
                try {
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
