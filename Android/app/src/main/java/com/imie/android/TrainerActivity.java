package com.imie.android;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imie.android.model.Position;
import com.imie.android.model.Trainer;
import com.imie.android.serviceWS.DataProvider;
import com.imie.android.serviceWS.TrainerWS;
import com.imie.android.util.Util;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class TrainerActivity extends AppCompatActivity {

    private final String MOVE_TO_NORTH = "north";
    private final String MOVE_TO_SOUTH = "south";
    private final String MOVE_TO_EAST = "east";
    private final String MOVE_TO_WEST = "west";

    private Trainer currentTrainer;

    private ImageView avatar;
    private TextView name;
    private TextView badges;

    private Button moveToNorth;
    private Button moveToSouth;
    private Button moveToEast;
    private Button moveToWest;

    private Retrofit retrofit;
    private TrainerWS trainerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

        avatar = (ImageView) findViewById(R.id.ivTrainerAvatar);
        name = (TextView) findViewById(R.id.tvTrainerName);
        badges = (TextView) findViewById(R.id.tvTrainerBadges);

        moveToNorth = (Button) findViewById(R.id.btnTrainerMoveNorth);
        moveToNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(MOVE_TO_NORTH);
            }
        });

        moveToSouth = (Button) findViewById(R.id.btnTrainerMoveSouth);
        moveToSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(MOVE_TO_SOUTH);
            }
        });

        moveToEast = (Button) findViewById(R.id.btnTrainerMoveEast);
        moveToEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(MOVE_TO_EAST);
            }
        });

        moveToWest = (Button) findViewById(R.id.btnTrainerMoveWest);
        moveToWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(MOVE_TO_WEST);
            }
        });

        avatar.setImageResource(R.drawable.sacha);

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
            .baseUrl(Util.getApiUrlBase(getApplicationContext()))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        // Get user login which is stored in sharedPreferences
        String login = Util.getSharedPreferences("userLogin", getApplicationContext());

        trainerService = retrofit.create(TrainerWS.class);

        // Get trainer informations by calling the symfony api
        Call<Trainer> pokemonItems = trainerService.getTrainer(login);
        pokemonItems.enqueue(new Callback<Trainer>() {
            @Override
            public void onResponse(Response<Trainer> response, Retrofit retrofit) {
                DataProvider.getInstance().setItem(response.body());

                Trainer trainer = null;

                if (response.body() != null) {
                    currentTrainer = response.body();

                    trainer = response.body();
                    name.setText("Nom: " + trainer.getName());

                    if (trainer.getBadges().size() > 0) {
                        for (int i = 0; i < trainer.getBadges().size(); i++) {
                            badges.setText("Badge(s) :" + trainer.getBadges().get(i).getName() + " ");
                        }
                    } else {
                        badges.setText("Aucun badge");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(), "L'utilisateur n'a pas été trouvé", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * move the trainer in a direction
     */
    private void move(String direction) {

        if (currentTrainer != null) {
            Position currentPosition = currentTrainer.getPosition();
            Position newPosition = new Position();

            if (direction.equals(MOVE_TO_NORTH)) {
                Float currentLat = currentPosition.getLatitude();
                Float newLat = currentLat + 0.002F;

                newPosition.setLatitude(newLat);
                newPosition.setLongitude(currentPosition.getLongitude());

            } else if (direction.equals(MOVE_TO_SOUTH)) {
                Float currentLat = currentPosition.getLatitude();
                Float newLat = currentLat - 0.002F;

                newPosition.setLatitude(newLat);
                newPosition.setLongitude(currentPosition.getLongitude());

            } else if (direction.equals(MOVE_TO_EAST)) {
                Float currentLgn = currentPosition.getLongitude();
                Float newLgn = currentLgn + 0.002F;

                newPosition.setLongitude(newLgn);
                newPosition.setLatitude(currentPosition.getLatitude());

            } else if (direction.equals(MOVE_TO_WEST)) {
                Float currentLgn = currentPosition.getLongitude();
                Float newLgn = currentLgn - 0.002F;

                newPosition.setLongitude(newLgn);
                newPosition.setLatitude(currentPosition.getLatitude());
            }

            updateMove(newPosition);
        }
    }


    /**
     * send new user position to symfony api
     * @param position
     */
    private void updateMove(Position position) {

        Call<String> item = trainerService.updateTrainerPosition(currentTrainer.getLogin(), position.getLatitude(), position.getLongitude());

        item.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if(response.body() != null) {
                    Toast.makeText(getApplication(), response.body(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(TrainerActivity.this, FightActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
