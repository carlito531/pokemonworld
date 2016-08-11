package com.imie.android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private ImageView avatar;
    private TextView name;
    private TextView badges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

        avatar = (ImageView) findViewById(R.id.ivTrainerAvatar);
        name = (TextView) findViewById(R.id.tvTrainerName);
        badges = (TextView) findViewById(R.id.tvTraineBadges);

        avatar.setImageDrawable(new ColorDrawable(Color.BLACK));

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get user login which is stored in sharedPreferences
        String login = Util.getSharedPreferences("userLogin", getApplicationContext());

        TrainerWS trainerService = retrofit.create(TrainerWS.class);

        // Get trainer informations by calling the symfony api
        Call<Trainer> pokemonItems = trainerService.getTrainer(login);
        pokemonItems.enqueue(new Callback<Trainer>() {
            @Override
            public void onResponse(Response<Trainer> response, Retrofit retrofit) {
                DataProvider.getInstance().setItem(response.body());

                Trainer trainer = null;

                if (response.body() != null) {
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
}
