package com.imie.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btPokedex;
    Button btFight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btPokedex = (Button) findViewById(R.id.btnHomePokedex);
        btFight = (Button)findViewById(R.id.btnHomeFight);

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
    }
}
