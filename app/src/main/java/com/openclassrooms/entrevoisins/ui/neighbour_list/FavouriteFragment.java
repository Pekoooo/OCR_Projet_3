package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.RemoveFavNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class FavouriteFragment extends Fragment implements MyNeighbourRecyclerViewAdapter.OnItemClickListener {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;


    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }


    private void initList() {
        mNeighbours = mApiService.getFavouriteNeighbourList();
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours, this));
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    

    @Subscribe
    public void onFavRemoveNeighbour(RemoveFavNeighbourEvent event){
        Log.d(TAG, "onFavRemoveNeighbour: is fired");
        mApiService.removeFavNeighbour(event.neighbour);
        initList();
    }


    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra("UserDetails", mNeighbours.get(position));

        startActivity(intent);
    }

}