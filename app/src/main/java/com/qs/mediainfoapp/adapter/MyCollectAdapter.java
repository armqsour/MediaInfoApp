package com.qs.mediainfoapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.component.PrepareView;
import com.google.gson.Gson;
import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.QsCallback;
import com.qs.mediainfoapp.entity.BaseResponse;
import com.qs.mediainfoapp.entity.VideoEntity;
import com.qs.mediainfoapp.listener.OnItemChildClickListener;
import com.qs.mediainfoapp.listener.OnItemClickListener;
import com.qs.mediainfoapp.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class MyCollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<VideoEntity> data;

    public void setData(List<VideoEntity> data) {
        this.data = data;
    }

    private OnItemChildClickListener mOnItemChildClickListener;

    private OnItemClickListener mOnItemClickListener;

    public MyCollectAdapter(Context context){
        this.mContext = context;
    }

    public MyCollectAdapter(Context context, List<VideoEntity> data){
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mycollect_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        VideoEntity videoEntity = data.get(position);
        viewHolder.tvTittle.setText(videoEntity.getVtitle());
        viewHolder.tvAuthor.setText(videoEntity.getAuthor());

//        通过Picasso库异步加载网络图片
        Picasso.with(mContext)
                .load(videoEntity.getHeadurl())
                .transform(new CircleTransform())
                .into(viewHolder.imgHeader);
        Picasso.with(mContext)
                .load(videoEntity.getCoverurl())
                .into(viewHolder.mThumb);
        viewHolder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        if(data != null && data.size() > 0){
            return data.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvTittle;
        private TextView tvAuthor;
        private ImageView imgHeader;
        public ImageView mThumb;
        public PrepareView mPrepareView;
        public FrameLayout mPlayerContainer;
        public int mPosition;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTittle = view.findViewById(R.id.title);
            tvAuthor = view.findViewById(R.id.author);
            imgHeader = view.findViewById(R.id.img_header);

            mPlayerContainer = view.findViewById(R.id.player_container);
            mPrepareView = view.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);
            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            if (mOnItemClickListener != null) {
                view.setOnClickListener(this);
            }

            //通过Tag将ViewHolder和ItemView绑定
            view.setTag(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player_container) {
                if (mOnItemChildClickListener != null) {
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            } else {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }
        }
    }
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
