package com.example.rapinderatest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageInfoFragment extends Fragment {
   ImageView imageView;
   TextView txtAlbumid,txtId,txtTitle,txtUrl;
    String albumid,id,url,title,thumbnailurl;

    public ImageInfoFragment() {
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_image_info, container, false);
        txtTitle=root.findViewById(R.id.title);
        imageView=root.findViewById(R.id.img);
        thumbnailurl = getArguments().getString("thumbnailurl");
        albumid = getArguments().getString("album_id");
        id = getArguments().getString("id");
        title = getArguments().getString("title");
        url = getArguments().getString("url");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no);
        requestOptions.error(R.drawable.no);

        Glide.with(getActivity()).setDefaultRequestOptions(requestOptions).load(thumbnailurl).into(imageView);
        //Picasso.with(getActivity()).load(thumbnailurl).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);


        txtTitle.setText(title);
        return root;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.image_info, menu);
        MenuItem item = menu.findItem(R.id.favorites);
        item.setVisible(false);
        MenuItem addf = menu.findItem(R.id.addfavorites);
        addf.setVisible(false);
        MenuItem info = menu.findItem(R.id.info);
        info.setVisible(false);
        MenuItem dowload = menu.findItem(R.id.download);
        dowload.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
