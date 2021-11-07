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

public class UserDetailActivity extends AppCompatActivity {

    private NeighbourApiService mApiService;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.openclassrooms.entrevoisins.R.layout.activity_user_detail);

        mApiService = DI.getNeighbourApiService();

        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("UserDetails")){
            Neighbour neighbour = getIntent().getParcelableExtra("UserDetails");

            setDetails(neighbour);


        }
    }



    private void setDetails(Neighbour neighbour){

        ImageView image = findViewById(R.id.MainImageView);
        TextView username1 = findViewById(R.id.on_picture_name);
        TextView username2 = findViewById(R.id.info_frame_name);
        TextView position = findViewById(R.id.info_frame_position);
        TextView cellphone = findViewById(R.id.info_frame_cellphone);
        TextView aboutMe = findViewById(R.id.about_me_details);
        TextView socials = findViewById(R.id.info_frame_socials);
        FloatingActionButton FavBtn = findViewById(R.id.fav_fab);


        Glide.with(this)
                .asBitmap()
                .load(neighbour.getAvatarUrl())
                .into(image);

        username1.setText(neighbour.getName());
        username2.setText(neighbour.getName());
        position.setText(neighbour.getAddress());
        cellphone.setText(neighbour.getPhoneNumber());
        aboutMe.setText(neighbour.getAboutMe());

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

        String UserSocials = getString(R.string.facebook_url, neighbour.getName());
        socials.setText(UserSocials);


    }
}