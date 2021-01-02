package com.qs.mediainfoapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.adapter.HomeAdapter;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.QsCallback;
import com.qs.mediainfoapp.entity.CategoryEntity;
import com.qs.mediainfoapp.entity.VideoCategoryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    @Override
    protected void initData() {
//        for (String tittle : mTitles) {
//            mFragments.add(VideoFragment.newInstance(tittle));
//        }
//        //预加载fragment,防止出错
//        viewPager.setOffscreenPageLimit(mFragments.size());
//        viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
//        slidingTabLayout.setViewPager(viewPager);
        getVideoCategoryList();
    }

    private void getVideoCategoryList() {
        HashMap<String, Object> params = new HashMap<>();
        Api.config(ApiConfig.VIDEO_CATEGORY_LIST, params).getRequest(getActivity(), new QsCallback() {
            @Override
            public void onSuccess(final String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        VideoCategoryResponse response = new Gson().fromJson(res, VideoCategoryResponse.class);
                        if (response != null && response.getCode() == 0) {
                            List<CategoryEntity> list = response.getPage().getList();
                            if (list != null && list.size() > 0) {
                                mTitles = new String[list.size()];
                                for (int i = 0; i < list.size(); i++) {
                                    mTitles[i] = list.get(i).getCategoryName();
                                    mFragments.add(VideoFragment.newInstance(list.get(i).getCategoryId()));
                                }
                                viewPager.setOffscreenPageLimit(mFragments.size());
                                viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
                                slidingTabLayout.setViewPager(viewPager);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}