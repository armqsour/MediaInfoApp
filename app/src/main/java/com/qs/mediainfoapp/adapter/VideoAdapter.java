package com.qs.mediainfoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qs.mediainfoapp.R;
import com.qs.mediainfoapp.entity.VideoEntity;
import com.qs.mediainfoapp.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<VideoEntity> data;

    public void setData(List<VideoEntity> data) {
        this.data = data;
    }

    public VideoAdapter(Context context){
        this.mContext = context;
        this.data = data;
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
        viewHolder.tvDz.setText(String.valueOf(120));
        viewHolder.tvCollect.setText(String.valueOf(121));
        viewHolder.tvComment.setText(String.valueOf(122));

//        viewHolder.imgCover.setImageResource(R.drawable.bg_witcher_ciri);

//        通过Picasso库异步加载网络图片
        Picasso.with(mContext)
                .load(videoEntity.getHeadurl())
                .transform(new CircleTransform())
                .into(viewHolder.imgHeader);
        Picasso.with(mContext).load(videoEntity.getCoverurl()).into(viewHolder.imgCover);
    }

    @Override
    public int getItemCount() {
        if(data != null && data.size() > 0){
            return data.size();
        }else{
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTittle;
        private TextView tvAuthor;
        private TextView tvDz;
        private TextView tvCollect;
        private TextView tvComment;
        private ImageView imgHeader;
        private ImageView imgCover;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTittle = view.findViewById(R.id.title);
            tvAuthor = view.findViewById(R.id.author);
            tvDz = view.findViewById(R.id.dz);
            tvCollect = view.findViewById(R.id.collect);
            tvComment = view.findViewById(R.id.comment);
            imgHeader = view.findViewById(R.id.img_header);
            imgCover = view.findViewById(R.id.img_cover);
        }
    }
}
