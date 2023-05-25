package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class NeighbourProfileActivity extends AppCompatActivity {
    private Neighbour mNeighbour;
    private long idNeighbour;
    private Boolean isFavorite;
    private FloatingActionButton mProfileFav;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private TextView mProfileName;
    private TextView mProfileInfoName;
    private TextView mProfileAdress;
    private TextView mProfilePhone;
    private TextView mProfileUrl;
    private TextView mProfileAboutText;
    private ImageView mProfileAvatar;
    private ImageButton mProfileBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profile);

        // Take instance of Action Bar using getSupportActionBar and
        // if it is not Null then call hide function
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mProfileName = findViewById(R.id.tvName);
        mProfileInfoName = findViewById(R.id.tvInfoName);
        mProfileAdress = findViewById(R.id.tvAdress);
        mProfilePhone = findViewById(R.id.tvPhone);
        mProfileUrl = findViewById(R.id.tvUrl);
        mProfileAboutText = findViewById(R.id.tvAboutText);
        mProfileAvatar = findViewById(R.id.ivAvatar);
        mProfileBtnBack = findViewById(R.id.ibtnBack);
        mProfileFav = findViewById(R.id.fabFav);

        idNeighbour = getIntent().getLongExtra("NEIGHBOUR", 0L);
        mApiService = DI.getNeighbourApiService();
        mNeighbours =  mApiService.getNeighbours();

        for (Neighbour neighbour : mNeighbours){
            if(neighbour.getId() == idNeighbour){
                mNeighbour = neighbour;
                break;
            }
        }

        mProfileBtnBack.setOnClickListener(view -> finish());

        mProfileName.setText(mNeighbour.getName());
        mProfileInfoName.setText(mNeighbour.getName());
        mProfileAdress.setText(mNeighbour.getAddress());
        mProfilePhone.setText(mNeighbour.getPhoneNumber());
        mProfileUrl.setText("www.facebook.fr/"+mNeighbour.getName().toLowerCase());
        mProfileAboutText.setText(mNeighbour.getAboutMe());

        Glide.with(this)
                .load(mNeighbour.getAvatarUrl())
                .into(mProfileAvatar);

        isFavorite = mNeighbour.isFavorite();
        setStateButtonFav(isFavorite);
        mProfileFav.setOnClickListener(view -> {
                    if(!isFavorite) {
                        isFavorite = true;
                        mNeighbour.setFavorite(isFavorite);
                        setStateButtonFav(isFavorite);
                    }
                    else{
                        isFavorite = false;
                        mNeighbour.setFavorite(isFavorite);
                        setStateButtonFav(isFavorite);
                    }
        });
    }

    private void setStateButtonFav(Boolean isFavorite) {
        if(!isFavorite)
            mProfileFav.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        else
            mProfileFav.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.fab_favorite)));
    }

    //Methode calquée sur AddNeighboorActivity pour lancer une nouvelle activité
    public static void navigate(Context context, Neighbour neighbour) {
        Intent intent = new Intent(context, NeighbourProfileActivity.class);
        intent.putExtra("NEIGHBOUR", neighbour.getId());
        ActivityCompat.startActivity(context, intent, null);


    }
}