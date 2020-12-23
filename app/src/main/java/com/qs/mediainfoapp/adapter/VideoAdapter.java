package com.qs.mediainfoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.entity.VideoEntity;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<VideoEntity> data;

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
        viewHolder.tvTittle.setText(videoEntity.getTittle());
        viewHolder.tvAuthor.setText(videoEntity.getName());
        viewHolder.tvDz.setText(String.valueOf(videoEntity.getDzCount()));
        viewHolder.tvCollect.setText(String.valueOf(videoEntity.getCollectCount()));
        viewHolder.tvComment.setText(String.valueOf(videoEntity.getCommentCount()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTittle;
        private TextView tvAuthor;
        private TextView tvDz;
        private TextView tvCollect;
        private TextView tvComment;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTittle = view.findViewById(R.id.title);
            tvAuthor = view.findViewById(R.id.author);
            tvDz = view.findViewById(R.id.dz);
            tvCollect = view.findViewById(R.id.collect);
            tvComment = view.findViewById(R.id.comment);
        }
    }
}
