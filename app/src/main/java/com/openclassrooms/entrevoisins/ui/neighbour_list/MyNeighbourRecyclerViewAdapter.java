package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static android.content.ContentValues.TAG;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.RemoveFavNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    private final OnItemClickListener mOnItemClickListener;
    private final boolean isFavourite;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, OnItemClickListener onItemClickListener, boolean isFavourite) {
        mNeighbours = items;
        this.mOnItemClickListener = onItemClickListener;
        this.isFavourite = isFavourite;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.fragment_neighbour, parent, false);
       return new ViewHolder(view, mOnItemClickListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);



        // Changes icon for deleting row from the list depending on the current tab.
        // This isFavourite = constructor isFavourite =/= Neighbour isFavourite

        if(isFavourite){
            holder.mDeleteButton.setBackgroundResource(R.drawable.ic_baseline_star_gold);
        } else {
            holder.mDeleteButton.setBackgroundResource(R.drawable.ic_delete_white_24dp);
        }


         // Changes event posted behind after delete button click depending on the current tab.
         holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if (!neighbour.isFavourite()) {

                     EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                 }

                 EventBus.getDefault().post(new RemoveFavNeighbourEvent(neighbour));
             }
         });

    }


    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        OnItemClickListener mOnItemClickListener;


        public ViewHolder(View view, OnItemClickListener mOnItemClickListener  ) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);

            this.mOnItemClickListener = mOnItemClickListener;

        }

        @Override
        public void onClick(View view) {

            mOnItemClickListener.onItemClick(getAdapterPosition());

        }
    }
}
