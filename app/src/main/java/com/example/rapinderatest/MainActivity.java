package com.example.rapinderatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pchmn.materialchips.util.ViewUtil.dpToPx;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getName();
    private ProgressDialog pd;
    ImageGridAdapter adapter;
    ArrayList<ImageList> subListArrayList = new ArrayList<ImageList>();
    ImageList subList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacingItemDecoration(10, dpToPx(5)));
        // recyclerView.addItemDecoration(new SpacingItemDecoration(2,dpToPx( 3), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);

        loadimagelist();

    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorites_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.favorites:
                Fragment myFragment = new FavoritesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment)
                        .addToBackStack("FavoritesFragment").commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadimagelist() {
        //creating a string request to send request to the url

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://jsonplaceholder.typicode.com/photos",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        //hiding the progressbar after completion
                        try {
                            JSONArray imageArray = new JSONArray(response);

                            //now looping through all the elements of the json array
                            for (int i = 0; i < imageArray.length(); i++) {

                                subList=new ImageList();
                                //getting the json object of the particular index inside the array
                                JSONObject imageObject = imageArray.getJSONObject(i);

                                //creating a image object and giving them the values from json object
                                ImageList image = new ImageList();
                                image.setAlbumid(imageObject.getString("albumId"));
                                image.setId(imageObject.getString("id"));
                                image.setTitle(imageObject.getString("title"));
                                image.setThumbnailurl(imageObject.getString("url"));
                                image.setUrl(imageObject.getString("thumbnailUrl"));

                                //adding the image to imagelist
                                subListArrayList.add(image);
                                adapter = new ImageGridAdapter(MainActivity.this, subListArrayList);
                                recyclerView.setAdapter(adapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}
