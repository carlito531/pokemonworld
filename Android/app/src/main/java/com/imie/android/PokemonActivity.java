package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.imie.android.ViewHelper.PokemonListViewAdapter;
import com.imie.android.serviceWS.DataProvider;
import com.imie.android.serviceWS.PokemonWS;
import com.imie.android.model.Pokemon;
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

        // Get pokemon list by calling the symfony api
        PokemonWS pokemonService = retrofit.create(PokemonWS.class);

        Call<List<Pokemon>> pokemonItems = pokemonService.getPokemonList();
        pokemonItems.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Response<List<Pokemon>> response, Retrofit retrofit) {
                DataProvider.getInstance().setItems(response.body());

                // fill the pokemon listView component with the response
                try {
                    PokemonListViewAdapter adapter = new PokemonListViewAdapter(PokemonActivity.this, response.body());
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
