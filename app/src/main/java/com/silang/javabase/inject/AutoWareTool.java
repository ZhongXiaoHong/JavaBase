package com.silang.javabase.inject;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.lang.reflect.Field;

public class AutoWareTool {

    public static void register(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(AutoWare.class)){
                AutoWare anno = field.getAnnotation(AutoWare.class);
                String value = anno.value();
                Intent intent = activity.getIntent();
                Object extraValue = intent.getSerializableExtra(value);
                field.setAccessible(true);
                try {
                    field.set(activity,extraValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
