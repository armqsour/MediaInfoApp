package com.qs.mediainfoapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.qs.mediainfoapp.activity.HomeActivity;
import com.qs.mediainfoapp.api.Api;
import com.qs.mediainfoapp.api.ApiConfig;
import com.qs.mediainfoapp.api.QsCallback;
import com.qs.mediainfoapp.entity.BaseResponse;
import com.qs.mediainfoapp.entity.LoginResponse;
import com.qs.mediainfoapp.entity.VideoEntity;
import com.qs.mediainfoapp.listener.OnItemChildClickListener;
import com.qs.mediainfoapp.listener.OnItemClickListener;
import com.qs.mediainfoapp.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<VideoEntity> data;

    public void setData(List<VideoEntity> data) {
        this.data = data;
    }

    private OnItemChildClickListener mOnItemChildClickListener;

    private OnItemClickListener mOnItemClickListener;

    public VideoAdapter(Context context){
        this.mContext = context;
    }

    public VideoAdapter(Context context, List<VideoEntity> data){
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        VideoEntity videoEntity = data.get(position);
        viewHolder.tvTittle.setText(videoEntity.getVtitle());
        viewHolder.tvAuthor.setText(videoEntity.getAuthor());
//        viewHolder.tvDz.setText(String.valueOf(120));
//        viewHolder.tvCollect.setText(String.valueOf(121));
//        viewHolder.tvComment.setText(String.valueOf(122));
        if (videoEntity.getVideoSocialEntity() != null) {
            int likenum = videoEntity.getVideoSocialEntity().getLikenum();
            int commentnum = videoEntity.getVideoSocialEntity().getCommentnum();
            int collectnum = videoEntity.getVideoSocialEntity().getCollectnum();
            boolean flagLike = videoEntity.getVideoSocialEntity().isFlagLike();
            boolean flagCollect = videoEntity.getVideoSocialEntity().isFlagCollect();
            viewHolder.tvDz.setText(String.valueOf(likenum));
            viewHolder.tvComment.setText(String.valueOf(commentnum));
            viewHolder.tvCollect.setText(String.valueOf(collectnum));
            viewHolder.flagCollect = flagCollect;
            viewHolder.flagLike = flagLike;
            if(flagLike){
                viewHolder.tvDz.setTextColor(Color.parseColor("#E21918"));
                viewHolder.imgDizan.setImageResource(R.mipmap.dianzan_select);
            }
            if(flagCollect){
                viewHolder.tvCollect.setTextColor(Color.parseColor("#E21918"));
                viewHolder.imgCollect.setImageResource(R.mipmap.collect_select);
            }
        }

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
        private TextView tvDz;
        private TextView tvCollect;
        private TextView tvComment;
        private ImageView imgHeader;

        private ImageView imgCollect;
        private ImageView imgDizan;

        public ImageView mThumb;
        public PrepareView mPrepareView;
        public FrameLayout mPlayerContainer;
        public int mPosition;
        private boolean flagCollect;
        private boolean flagLike;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTittle = view.findViewById(R.id.title);
            tvAuthor = view.findViewById(R.id.author);
            tvDz = view.findViewById(R.id.dz);
            tvCollect = view.findViewById(R.id.collect);
            tvComment = view.findViewById(R.id.comment);
            imgHeader = view.findViewById(R.id.img_header);

            imgCollect = view.findViewById(R.id.img_collect);
            imgDizan = view.findViewById(R.id.img_like);

            mPlayerContainer = view.findViewById(R.id.player_container);
            mPrepareView = view.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);
            if (mOnItemChildClickListener != null) {
                mPlayerContainer.setOnClickListener(this);
            }
            if (mOnItemClickListener != null) {
                view.setOnClickListener(this);
            }

            imgCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int collectNum = Integer.parseInt(tvCollect.getText().toString());
                    if(flagCollect){
                        if (collectNum > 0) {
                            tvCollect.setText(String.valueOf(--collectNum));
                            tvCollect.setTextColor(Color.parseColor("#161616"));
                            imgCollect.setImageResource(R.mipmap.collect);
                            updateCount(data.get(mPosition).getVid(), 1, !flagCollect);
                        }
                    } else{
                        tvCollect.setText(String.valueOf(++collectNum));
                        tvCollect.setTextColor(Color.parseColor("#E21918"));
                        imgCollect.setImageResource(R.mipmap.collect_select);
                        updateCount(data.get(mPosition).getVid(), 1, !flagCollect);
                    }
                    flagCollect = !flagCollect;
                }
            });

            imgDizan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeNum = Integer.parseInt(tvDz.getText().toString());
                    if(flagLike){
                        if (likeNum > 0) {
                            tvDz.setText(String.valueOf(--likeNum));
                            tvDz.setTextColor(Color.parseColor("#161616"));
                            updateCount(data.get(mPosition).getVid(), 2, !flagLike);
                            imgDizan.setImageResource(R.mipmap.dianzan);
                        }
                    } else{
                        tvDz.setText(String.valueOf(++likeNum));
                        tvDz.setTextColor(Color.parseColor("#E21918"));
                        imgDizan.setImageResource(R.mipmap.dianzan_select);
                        updateCount(data.get(mPosition).getVid(), 2, !flagLike);
                    }
                    flagLike = !flagLike;
                }
            });

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

    private void updateCount(int vid, int type, boolean flag){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("vid", vid);
        params.put("type", type);
        params.put("flag", flag);
        Api.config(ApiConfig.VIDEO_UPDATE_COUNT, params).postRequest(mContext, new QsCallback() {
            @Override
            public void onSuccess(String res) {
                Log.d("token(LoginActivity)", res);
                Gson gson = new Gson();
                BaseResponse baseResponse = gson.fromJson(res, BaseResponse.class);
                if(baseResponse.getCode() == 0){

                }else{

                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.toString());
            }
        });
    }
}
