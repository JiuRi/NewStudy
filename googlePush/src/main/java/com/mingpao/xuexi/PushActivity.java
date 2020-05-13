package com.mingpao.xuexi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PushActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_push);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTittle = findViewById(R.id.toolbar_tittle);
        toolbarTittle.setText("测试 ClickAction 页面");
        setSupportActionBar(toolbar);

    }
}
