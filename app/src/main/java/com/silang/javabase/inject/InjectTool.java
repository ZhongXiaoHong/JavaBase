package com.silang.javabase.inject;

import android.app.Activity;

import java.lang.reflect.Field;

public class InjectTool {

    public static void register(Activity activity){


        Field[] field = activity.getClass().getDeclaredFields();

    }
}
