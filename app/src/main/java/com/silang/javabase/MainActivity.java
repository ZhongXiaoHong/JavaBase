package com.silang.javabase;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.silang.javabase.inject.Inject;
import com.silang.javabase.inject.InjectTool;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {


    @Inject(id = R.id.tv10086)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectTool.register(this);
        textView.setText("10086");


    }

    @RequiresPermission(allOf = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void jump(View view) {

        Intent intent = new Intent(this,SencondActivity.class);
        intent.putExtra("age",28);
        intent.putExtra("name","hong");
        intent.putExtra("height",188.50f);
        intent.putExtra("data",new Data("序列化"));
        startActivity(intent);


    }
}