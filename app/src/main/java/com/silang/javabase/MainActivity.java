package com.silang.javabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.silang.javabase.caoniao.ViewById;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    @ViewById(value=1,name="")
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}