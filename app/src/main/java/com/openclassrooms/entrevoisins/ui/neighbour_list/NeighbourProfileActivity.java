package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

public class NeighbourProfileActivity extends AppCompatActivity {
    private Neighbour neighbour;
    private long idNeighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profile);

        Intent intent = getIntent();
        intent.getLongExtra("NEIGHBOUR", idNeighbour);
        neighbour =
    }

    //Methode calquée à partir de AddNeighboorActivity pour lancer une nouvelle activité
    public static void navigate(Context context, Neighbour neighbour) {
        Intent intent = new Intent(context, NeighbourProfileActivity.class);
        ActivityCompat.startActivity(context, intent, null);
        intent.putExtra("NEIGHBOUR", neighbour.getId());
    }
}