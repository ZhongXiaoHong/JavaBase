package com.silang.javabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.silang.javabase.inject.AutoWare;
import com.silang.javabase.inject.AutoWareTool;
import com.silang.javabase.inject.Inject;
import com.silang.javabase.inject.InjectTool;

public class SencondActivity extends AppCompatActivity {

    @AutoWare("age")
    int age;
    @AutoWare("height")
    float height;
    @AutoWare("data")
    Data data;
    @AutoWare("name")
    String name;

    @Inject(id = R.id.tv)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencond);
        AutoWareTool.register(this);
        InjectTool.register(this);
        textView.setText(age + " " + height + data.toString() + name);

    }
}