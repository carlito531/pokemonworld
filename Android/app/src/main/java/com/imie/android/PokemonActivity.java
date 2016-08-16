package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.imie.android.ViewHelper.PokemonListViewAdapter;
import com.imie.android.model.Trainer;
import com.imie.android.serviceWS.DataProvider;
import com.imie.android.serviceWS.PokemonWS;
import com.imie.android.model.Pokemon;
import com.imie.android.serviceWS.TrainerWS;
import com.imie.android.util.Util;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PokemonActivity extends AppCompatActivity {

    private ListView pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        pokemonList = (ListView) findViewById(R.id.lvPokemons);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Get connected user login
        String login = Util.getSharedPreferences("userLogin", getApplicationContext());

        // Get pokemon list by calling the symfony api
        TrainerWS trainerService = retrofit.create(TrainerWS.class);

        Call<Trainer> pokemonItems = trainerService.getTrainer(login);
        pokemonItems.enqueue(new Callback<Trainer>() {
            @Override
            public void onResponse(Response<Trainer> response, Retrofit retrofit) {
                DataProvider.getInstance().setItem(response.body());

                Trainer trainer = null;

                if(response.body() != null) {
                    trainer = response.body();
                }

                // fill the pokemon listView component with the response
                try {
                    PokemonListViewAdapter adapter = new PokemonListViewAdapter(PokemonActivity.this, trainer.getPokemon());
                    pokemonList.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
