package com.qs.mediainfoapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qs.mediainfoapp.activity.BaseActivity;
import com.qs.mediainfoapp.activity.LoginActivity;
import com.qs.mediainfoapp.activity.RegisterActivity;

public class MainActivity extends BaseActivity {

    private Button btnLogin;
    private Button btnRegister;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnLogin = findViewById(R.id.btn_login);
//        btnRegister = findViewById(R.id.btn_register);
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateTo(LoginActivity.class);
//            }
//        });
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateTo(RegisterActivity.class);
//            }
//        });
//    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    public void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });
    }
}