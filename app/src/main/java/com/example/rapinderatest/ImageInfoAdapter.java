package com.example.rapinderatest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageInfoAdapter  extends  RecyclerView.Adapter<ImageInfoAdapter.MyView>  {

    private Context mContext;
    private List<ImageList> subList;
    private ArrayList<ImageList> arraylist;

    public class MyView extends RecyclerView.ViewHolder {
        public ImageView image;
        TextView title;

        public MyView(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.img);
            title=(TextView)view.findViewById(R.id.title);
        }
    }

    public ImageInfoAdapter(Context mContext, ArrayList<ImageList> albumList) {
        this.mContext = mContext;
        this.subList = albumList;
        this.arraylist = new ArrayList<ImageList>();
        this.arraylist.addAll(albumList);
    }

    @Override
    public ImageInfoAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_info, parent, false);

        return new ImageInfoAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageInfoAdapter.MyView holder, int position) {

        final ImageList enq = subList.get(position);

        Glide.with(mContext).load(enq.getThumbnailurl()).placeholder(R.drawable.no).into(holder.image);
        holder.title.setText(enq.getTitle());

    }
    @Override
    public int getItemCount() {
        return subList.size();
    }

}




