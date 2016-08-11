package com.imie.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imie.android.model.Trainer;

public class HomeActivity extends AppCompatActivity {

    Button btPokedex;
    Button btFight;
    Button btPokemon;
    Button btTrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btPokedex = (Button) findViewById(R.id.btnHomePokedex);
        btFight = (Button)findViewById(R.id.btnHomeFight);
        btPokemon = (Button)findViewById(R.id.btnHomePokemon);
        btTrainer = (Button)findViewById(R.id.btnHomeTrainer);

        // go to the pokedex screen
        btPokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PokedexActivity.class);
                startActivity(intent);
            }
        });

        // go to the fight screen
        btFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FightActivity.class);
                startActivity(intent);
            }
        });


        // go to the pokemon screen
        btPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PokemonActivity.class);
                startActivity(intent);
            }
        });


        // go to the pokemon screen
        btTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TrainerActivity.class);
                startActivity(intent);
            }
        });
    }
}
