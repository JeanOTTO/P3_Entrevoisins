package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityNeighbourProfileBinding;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class NeighbourProfileActivity extends AppCompatActivity {
    private Neighbour mNeighbour;
    private long idNeighbour;
    private Boolean isFavorite;
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private ActivityNeighbourProfileBinding binding;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNeighbourProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        idNeighbour = getIntent().getLongExtra("NEIGHBOUR", 0L);
        mApiService = DI.getNeighbourApiService();
        mNeighbours =  mApiService.getNeighbours();

        //Init le voisin on cherchant dans la liste avec l'ID
        for (Neighbour neighbour : mNeighbours){
            if(neighbour.getId() == idNeighbour){
                mNeighbour = neighbour;
                break;
            }
        }

        //mNeighbours.stream().filter(neighbour -> neighbour.getId()==idNeighbour).findFirst();

        binding.ibtnBack.setOnClickListener(v -> finish());
        binding.tvName.setText(mNeighbour.getName());
        binding.tvInfoName.setText(mNeighbour.getName());
        binding.tvAdress.setText(mNeighbour.getAddress());
        binding.tvPhone.setText(mNeighbour.getPhoneNumber());
        binding.tvUrl.setText("www.facebook.fr/"+mNeighbour.getName().toLowerCase());
        binding.tvAboutText.setText(mNeighbour.getAboutMe());

        Glide.with(this)
                .load(mNeighbour.getAvatarUrl())
                .into(binding.ivAvatar);

        isFavorite = mNeighbour.isFavorite();
        setStateButtonFav(isFavorite);

        binding.fabFav.setOnClickListener(v -> {
            setNeighbourFav();
        });
    }

    private void setNeighbourFav(){
        if(!isFavorite) {
            isFavorite = true;
        }
        else{
            isFavorite = false;
        }
        mNeighbour.setFavorite(isFavorite);
        setStateButtonFav(isFavorite);
    }

    private void setStateButtonFav(Boolean isFavorite) {
        int color;
        if(!isFavorite)
            color = getResources().getColor(R.color.black);
        else
            color = getResources().getColor(R.color.fab_favorite);
        binding.fabFav.setImageTintList(ColorStateList.valueOf(color));
    }

    //Methode calquée sur AddNeighboorActivity pour lancer une nouvelle activité
    public static void navigate(Context context, Neighbour neighbour) {
        Intent intent = new Intent(context, NeighbourProfileActivity.class);
        intent.putExtra("NEIGHBOUR", neighbour.getId());
        ActivityCompat.startActivity(context, intent, null);
    }
}