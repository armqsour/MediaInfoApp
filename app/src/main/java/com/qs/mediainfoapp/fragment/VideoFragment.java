package com.qs.mediainfoapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.activity.LoginActivity;
import com.qs.mediainfoapp.adapter.VideoAdapter;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.QsCallback;
import com.qs.mediainfoapp.entity.VideoEntity;
import com.qs.mediainfoapp.entity.VideoListResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private int pageNum = 1;
    private VideoAdapter videoAdapter;
    List<VideoEntity> datas = new ArrayList<>();

    public VideoFragment() {
    }

    public static VideoFragment newInstance(String tittle) {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        refreshLayout = v.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh(2000);
                pageNum = 1;
                getVideoList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishLoadMore(2000);
                pageNum++;
                getVideoList(false);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        videoAdapter = new VideoAdapter(getActivity());
        recyclerView.setAdapter(videoAdapter);
        getVideoList(true);
    }

    private void getVideoList(boolean isRefresh){
        String token = getStringFromSP("token");
        if(!TextUtils.isEmpty(token)){
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", token);
            params.put("page", pageNum);
            params.put("limit", ApiConfig.PAGE_SIZE);
            Api.config(ApiConfig.VIDEO_LIST, params).getRequest(new QsCallback() {
                @Override
                public void onSuccess(final String res) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isRefresh){
                                refreshLayout.finishRefresh(true);
                            }else{
                                refreshLayout.finishLoadMore(true);
                            }
                            VideoListResponse response = new Gson().fromJson(res, VideoListResponse.class);
                            if(response != null && response.getCode()==0){
                                List<VideoEntity> list = response.getPage().getList();
                                if(list != null && list.size() >0){
                                    if(isRefresh){
                                        datas = list;
                                    }else{
                                        datas.addAll(list);
                                    }
                                    videoAdapter.setData(datas);
                                    videoAdapter.notifyDataSetChanged();
                                }else{
                                    if(isRefresh){
                                        showToast("暂时无法刷新数据");
                                    }else{
                                        showToast("没有更多数据");
                                    }
                                }
                            }
                            Log.d("token", res);
                        }
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    if(isRefresh){
                        refreshLayout.finishRefresh(true);
                    }else{
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });
        }else{
            navigateTo(LoginActivity.class);
        }
    }
}