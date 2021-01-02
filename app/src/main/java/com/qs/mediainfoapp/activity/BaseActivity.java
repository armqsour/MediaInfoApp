package com.qs.mediainfoapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import com.dueeeke.videoplayer.player.VideoViewManager;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayout());
        initView();
        initData();
    }

    public abstract int initLayout();

    public abstract void initView();

    public abstract void initData();

    public void showToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg){
        Looper.prepare();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    

    public void navigateTo(Class cls){
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    public void navigateToWithFlag(Class cls, int flags){
        Intent intent = new Intent(mContext, cls);
        intent.setFlags(flags);
        startActivity(intent);
    }


    protected void saveStringToSP(String key, String value){
        SharedPreferences sp = getSharedPreferences("sp_qs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected VideoViewManager getVideoViewManager(){
        return VideoViewManager.instance();
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }


}
