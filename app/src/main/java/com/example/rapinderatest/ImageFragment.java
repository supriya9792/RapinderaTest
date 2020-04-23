package com.example.rapinderatest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
ImageView imageView;
DatabaseHelper db;
String albumid,id,url,title,thumbnailurl;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_blank, container, false);
            imageView=root.findViewById(R.id.image);

            db=new DatabaseHelper(getActivity());
         thumbnailurl = getArguments().getString("thumbnailurl");
         albumid = getArguments().getString("album_id");
         id = getArguments().getString("id");
         title = getArguments().getString("title");
         url = getArguments().getString("url");
        Glide.with(getActivity()).load(thumbnailurl).placeholder(R.drawable.no).into(imageView);

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

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addfavorites:
              long res=  db.insertFavImage(albumid,id,title,url,thumbnailurl);
              if(res>-1){
                  Toast.makeText(getActivity(), "Image Added to Favorites", Toast.LENGTH_SHORT).show();
              }
                return true;
            case R.id.info:

                ImageInfoFragment imageInfoFragment = new ImageInfoFragment ();
                Bundle bundle = new Bundle();
                bundle.putString("thumbnailurl", thumbnailurl);
                bundle.putString("album_id",albumid);
                bundle.putString("id", id);
                bundle.putString("title", title);
                bundle.putString("url", url);
                imageInfoFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, imageInfoFragment).addToBackStack("ImageInfoFragment").commit();
               // Toast.makeText(getActivity(), "Clear call log", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.download:
                DownloadImage(thumbnailurl);
               // Toast.makeText(getActivity(), "Clear call log", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class DownloadsImage extends AsyncTask<String, Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory( "/RapinderaTest"); //Creates app specific folder

            if(!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".png"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(getContext(),new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        // Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getContext(),"Image Saved",Toast.LENGTH_SHORT).show();

            //showToast("Image Saved!");
        }
    }
    void DownloadImage(String ImageUrl) {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            //  showToast("Need Permission to access storage for Downloading Image");
        } else {
            Toast.makeText(getContext(),"Downloading Image...",Toast.LENGTH_SHORT).show();
            //Asynctask to create a thread to downlaod image in the background
            new DownloadsImage().execute(ImageUrl);
        }
    }
}