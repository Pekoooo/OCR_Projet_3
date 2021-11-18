package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailActivity extends AppCompatActivity {

    @BindView(R.id.MainImageView)
    public ImageView image;
    @BindView(R.id.on_picture_name)
    public TextView username1;
    @BindView(R.id.info_frame_name)
    public TextView username2;
    @BindView(R.id.info_frame_position)
    public TextView position;
    @BindView(R.id.info_frame_cellphone)
    public TextView cellphone;
    @BindView(R.id.about_me_details)
    public TextView aboutMe;
    @BindView(R.id.info_frame_socials)
    public TextView socials;
    @BindView(R.id.fav_fab)
    public FloatingActionButton FavBtn;



    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.openclassrooms.entrevoisins.R.layout.activity_user_detail);

        mApiService = DI.getNeighbourApiService();

        getIncomingIntent();

        //Removes the name of the app in actionbar
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("");
        }


    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("UserDetails")){
            Neighbour neighbour = getIntent().getParcelableExtra("UserDetails");

            setDetails(neighbour);
            setFavFab(neighbour);
        }
    }


    private void setDetails(Neighbour neighbour){
        ButterKnife.bind(this);

        Glide.with(this)
                .asBitmap()
                .load(neighbour.getAvatarUrl())
                .into(image);
        username1.setText(neighbour.getName());
        username2.setText(neighbour.getName());
        position.setText(neighbour.getAddress());
        cellphone.setText(neighbour.getPhoneNumber());
        aboutMe.setText(neighbour.getAboutMe());

        //Sets social media link with a placeholder :
        String UserSocials = getString(R.string.facebook_url, neighbour.getName());
        socials.setText(UserSocials);
    }


    private void setFavFab(Neighbour neighbour){

        if(mApiService.getFavouriteNeighbourList().contains(neighbour)){
            FavBtn.setColorFilter(getResources().getColor(R.color.FavColor));
        } else {
            FavBtn.setColorFilter(getResources().getColor(R.color.NotFavColor));
        }


        FavBtn.setOnClickListener(view -> {
            if(!mApiService.getFavouriteNeighbourList().contains(neighbour)){
                mApiService.addFavNeighbour(neighbour);
                FavBtn.setColorFilter(getResources().getColor(R.color.FavColor));
            } else {
                mApiService.removeFavNeighbour(neighbour);
                FavBtn.setColorFilter(getResources().getColor(R.color.NotFavColor));
            }

        });

    }
}