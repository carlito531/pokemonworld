package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imie.android.serviceWS.DataProvider;
import com.imie.android.serviceWS.PokemonWS;

import com.imie.android.model.Pokemon;
import com.imie.android.util.Util;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by rvano_000 on 7/20/2016.
 */
public class PokedexActivity extends AppCompatActivity {

    private EditText nameSearch;
    private TextView name;
    private TextView type;
    private TextView attack1;
    private TextView attack2;
    private TextView attack3;
    private TextView attack4;
    private Button rechercher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        nameSearch = (EditText)findViewById(R.id.pokedexEt);
        name = (TextView)findViewById(R.id.pokedexPokemonNameTv);
        type = (TextView)findViewById(R.id.pokedexPokemonTypeTv);
        attack1 = (TextView)findViewById(R.id.pokedexPokemonAttaque1);
        attack2 = (TextView)findViewById(R.id.pokedexPokemonAttaque2);
        attack3 = (TextView)findViewById(R.id.pokedexPokemonAttaque3);
        attack4 = (TextView)findViewById(R.id.pokedexPokemonAttaque4);
        rechercher = (Button)findViewById(R.id.pokedexRechercher);
        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPokemon();
            }
        });
    }


    /**
     * get the pokemon informations and fill the pokedex
     */
    public void getPokemon(){

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get pokemon searched by calling the symfony api
        PokemonWS service = retrofit.create(PokemonWS.class);

        Call<Pokemon> item = service.getPokemon(nameSearch.getText().toString());
        item.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Response<Pokemon> response, Retrofit retrofit) {
                DataProvider.getInstance().setItem(response.body());

                Pokemon pokemonResult = response.body();

                if (pokemonResult != null) {
                    name.setText("Nom: " + pokemonResult.getName());
                    name.setVisibility(View.VISIBLE);
                    type.setText("Type: " + pokemonResult.getPokemonType().getName());
                    type.setVisibility(View.VISIBLE);
                    attack1.setText("Attaque 1: " + pokemonResult.getAttack1().getName());
                    attack1.setVisibility(View.VISIBLE);
                    attack2.setText("Attaque 2: " + pokemonResult.getAttack2().getName());
                    attack2.setVisibility(View.VISIBLE);
                    attack3.setText("Attaque 3: " + pokemonResult.getAttack3().getName());
                    attack3.setVisibility(View.VISIBLE);
                    attack4.setText("Attaque 4: " + pokemonResult.getAttack4().getName());
                    attack4.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplication(),"Pokemon non trouvé", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(),"Le serveur ne répond pas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
