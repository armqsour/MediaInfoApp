package com.qs.mediainfoapp.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.activity.LoginActivity;
import com.qs.mediainfoapp.activity.MyCollectActivity;
//import com.qs.mediainfoapp.activity.MyCollectActivity;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.SkinCompatManager;
//import skin.support.SkinCompatManager;


public class MyFragment extends BaseFragment {

    @BindView(R.id.img_header)
    ImageView imgHeader;
    @BindView(R.id.name)
    TextView tv_name;

    public MyFragment() {
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tv_name.setText(getStringFromSP("userName"));
    }

    @OnClick({R.id.img_header, R.id.rl_collect, R.id.rl_skin, R.id.rl_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header:

                break;
            case R.id.rl_collect:
                navigateTo(MyCollectActivity.class);
                break;
            case R.id.rl_skin:
                String skin = getStringFromSP("skin");
                if (skin.equals("night")) {
                    // 恢复应用默认皮肤
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    saveStringToSP("skin", "default");
                } else {
                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                    saveStringToSP("skin", "night");
                }
                break;
            case R.id.rl_logout:
                removeByKey("token");
                navigateToWithFlag(LoginActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
        }
    }
}