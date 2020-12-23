package com.qs.mediainfoapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.adapter.VideoAdapter;
import com.qs.mediainfoapp.entity.VideoEntity;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {

    private String tittle;

    public VideoFragment() {
    }

    public static VideoFragment newInstance(String tittle) {
        VideoFragment fragment = new VideoFragment();
        fragment.tittle = tittle;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<VideoEntity> data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            VideoEntity videoEntity = new VideoEntity();
            videoEntity.setTittle("韭菜盒子新做法，不发面不烫面");
            videoEntity.setName("大胃王");
            videoEntity.setDzCount(i*2);
            videoEntity.setCollectCount(i*2);
            videoEntity.setCommentCount(i*3);
            data.add(videoEntity);
        }
        VideoAdapter videoAdapter = new VideoAdapter(getActivity(), data);
        recyclerView.setAdapter(videoAdapter);
        return v;
    }
}