package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.RemoveFavNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class NeighbourFragment extends Fragment implements MyNeighbourRecyclerViewAdapter.OnItemClickListener {

    private  NeighbourApiService mApiService;
    private  List<Neighbour> mNeighbours;
    private  RecyclerView mRecyclerView;
    public static final String fragment_key = "position";
    private int position;

    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */

    public static NeighbourFragment newInstance(int position) {
        Log.d(TAG, "newInstance: is called");

        // Sends a fragment with a bundle
        Bundle bundle = new Bundle();
        bundle.putInt(fragment_key, position);
        NeighbourFragment fragment = new NeighbourFragment();
        fragment.setArguments(bundle);


        return fragment;

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
        position = getArguments().getInt(fragment_key);
        initList();
        return view;
    }


    /**
     * Init the List of neighbours
     */
    private void initList() {

        mNeighbours = position == 0 ? mApiService.getNeighbours() : mApiService.getFavouriteNeighbourList();

        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours, this, position == 1));
                                                                                                      //Reminder : isFavourite ONLY if position == 1
                                                                                                        //if not 0 = isFavourite == false
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

     /**
      * Fired if the user clicks on a delete button
      * @param event
      */

     // Code fired when Delete or Remove is clicked
     @Subscribe
     public void onDeleteNeighbour(DeleteNeighbourEvent event) {

         mApiService.deleteNeighbour(event.neighbour);
         initList();
     }

    @Subscribe
    public void onRemoveFavoriteNeighbour(RemoveFavNeighbourEvent event) {

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
