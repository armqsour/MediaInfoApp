package com.qs.mediainfoapp.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.QsCallback;
import com.qs.mediainfoapp.entity.LoginResponse;

import java.util.HashMap;

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
        Api.config(ApiConfig.LOGIN, params).postRequest(new QsCallback() {
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