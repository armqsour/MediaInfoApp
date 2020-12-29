package com.qs.mediainfoapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.activity.LoginActivity;
import com.qs.mediainfoapp.activity.WebActivity;
import com.qs.mediainfoapp.adapter.NewsAdapter;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.QsCallback;
import com.qs.mediainfoapp.entity.NewsEntity;
import com.qs.mediainfoapp.entity.NewsListResponse;
import com.qs.mediainfoapp.entity.VideoEntity;
import com.qs.mediainfoapp.entity.VideoListResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private NewsAdapter newsAdapter;
    private List<NewsEntity> datas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
//                showToast("点击");
                NewsEntity newsEntity = (NewsEntity) obj;
//                String url = "http://192.168.56.1:8089/newsDetail?tittle=" + newsEntity.getAuthorName();
                String url = "https://www.3dmgame.com/news/202007/3794255.html";
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                navigateToWithBundle(WebActivity.class, bundle);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh(2000);
                pageNum = 1;
                getNewsList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishLoadMore(2000);
                pageNum++;
                getNewsList(false);
            }
        });
        getNewsList(true);
    }



    private void getNewsList(boolean isRefresh){
        String token = getStringFromSP("token");
            HashMap<String, Object> params = new HashMap<>();
            params.put("page", pageNum);
            params.put("limit", ApiConfig.PAGE_SIZE);
            Api.config(ApiConfig.NEWS_LIST, params).getRequest(getActivity(), new QsCallback() {
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
                            NewsListResponse response = new Gson().fromJson(res, NewsListResponse.class);
                            if(response != null && response.getCode()==0){
                                List<NewsEntity> list = response.getPage().getList();
                                if(list != null && list.size() >0){
                                    if(isRefresh){
                                        datas = list;
                                    }else{
                                        datas.addAll(list);
                                    }
                                    newsAdapter.setData(datas);
                                    newsAdapter.notifyDataSetChanged();
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
    }
}