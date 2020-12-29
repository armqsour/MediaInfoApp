package com.qs.mediainfoapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dueeeke.videoplayer.player.VideoViewManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(initLayout(), container, false);
            initView();
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();


    public void showToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg){
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void navigateTo(Class cls){
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    public void navigateToWithBundle(Class cls, Bundle bundle){
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void navigateToWithFlag(Class cls, int flags){
        Intent intent = new Intent(getActivity(), cls);
        intent.setFlags(flags);
        startActivity(intent);
    }

    protected void saveStringToSP(String key, String value){
        SharedPreferences sp = getActivity().getSharedPreferences("sp_qs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String getStringFromSP(String key){
        SharedPreferences sp = getActivity().getSharedPreferences("sp_qs", MODE_PRIVATE);
        return  sp.getString(key, "");
    }

    protected void removeByKey(String key){
        SharedPreferences sp = getActivity().getSharedPreferences("sp_qs", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }

    protected VideoViewManager getVideoViewManager(){
        return VideoViewManager.instance();
    }
}
