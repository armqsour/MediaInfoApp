package com.qs.mediainfoapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.LongDef;

import com.google.gson.Gson;
import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.Callback;
import com.qs.mediainfoapp.entity.LoginResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        etAccount = findViewById(R.id.et_account);
//        etPwd = findViewById(R.id.et_pwd);
//        btnLogin = findViewById(R.id.btn_login);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String account = etAccount.getText().toString().trim();
//                String pwd = etPwd.getText().toString().trim();
//                login(account, pwd);
//            }
//        });
//    }

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account, pwd);
            }
        });
    }

    private void login(String account, String pwd){
        if(TextUtils.isEmpty(account)){
            showToast("请输入账号");
            return;
        }

        if(TextUtils.isEmpty(pwd)){
            showToast("请输入密码");
            return;
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", account);
        params.put("password", pwd);
        Api.config(ApiConfig.LOGIN, params).postRequest(new Callback() {
            @Override
            public void onSuccess(String res) {
                Log.d("Token", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if(loginResponse.getCode() == 0){
                    String token = loginResponse.getToken();
                    saveStringToSP("token", token);
                    navigateTo(HomeActivity.class);
                    showToastSync("登录成功");
                }else{
                    showToastSync("登陆失败");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.toString());
            }
        });

    }
}