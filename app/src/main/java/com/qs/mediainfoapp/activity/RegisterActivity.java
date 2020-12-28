package com.qs.mediainfoapp.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.QsCallback;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnRegister;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        etAccount = findViewById(R.id.et_account);
//        etPwd = findViewById(R.id.et_pwd);
//        btnRegister = findViewById(R.id.btn_register);
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String account = etAccount.getText().toString().trim();
//                String pwd = etPwd.getText().toString().trim();
//                register(account, pwd);
//            }
//        });
//    }

    @Override
    public int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    public void initData() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                register(account, pwd);
            }
        });
    }

    private void register(String account, String pwd){
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
        Api.config(ApiConfig.Register, params).postRequest(new QsCallback() {
            @Override
            public void onSuccess(String res) {
                showToastSync(res);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.toString());
            }
        });

    }
}