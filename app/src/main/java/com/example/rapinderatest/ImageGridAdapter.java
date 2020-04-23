package com.example.rapinderatest;

import android.content.Context;
import android.content.Intent;
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

public class ImageGridAdapter extends  RecyclerView.Adapter<ImageGridAdapter.MyView>  {

    private Context mContext;
    private List<ImageList> subList;
    private ArrayList<ImageList> arraylist;

    public class MyView extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyView(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);

        }
    }

    public ImageGridAdapter(Context mContext, ArrayList<ImageList> albumList) {
        this.mContext = mContext;
        this.subList = albumList;
        this.arraylist = new ArrayList<ImageList>();
        this.arraylist.addAll(albumList);
    }

    @Override
    public ImageGridAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_grid_list, parent, false);

        return new ImageGridAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageGridAdapter.MyView holder, int position) {

        final ImageList enq = subList.get(position);

       Glide.with(mContext).load(enq.getThumbnailurl()).placeholder(R.drawable.no).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) mContext;
                Fragment myFragment = new ImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("thumbnailurl", enq.getThumbnailurl());
                bundle.putString("album_id", enq.getAlbumid());
                bundle.putString("id", enq.getId());
                bundle.putString("title", enq.getTitle());
                bundle.putString("url", enq.getUrl());
               myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack("ImageFragment").commit();

            }
        });

    }


    @Override
    public int getItemCount() {
        return subList.size();
    }

}



