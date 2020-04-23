package com.example.rapinderatest;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;

import java.util.ArrayList;

import static com.pchmn.materialchips.util.ViewUtil.dpToPx;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    public static String TAG = FavoritesFragment.class.getName();
    private ProgressDialog pd;
    ImageInfoAdapter adapter;
    ArrayList<ImageList> subListArrayList = new ArrayList<ImageList>();
    ImageList subList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    DatabaseHelper db;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView =root. findViewById(R.id.recycler_view);
        db=new DatabaseHelper(getContext());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacingItemDecoration(10, dpToPx(5)));
        // recyclerView.addItemDecoration(new SpacingItemDecoration(2,dpToPx( 3), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);

        subListArrayList=db.getAllFavorites();
        if (subListArrayList.size()>0) {
            adapter = new ImageInfoAdapter(getContext(), subListArrayList);
            recyclerView.setAdapter(adapter);
        }
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
