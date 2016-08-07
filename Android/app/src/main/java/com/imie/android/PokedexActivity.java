package com.imie.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imie.android.ViewHelper.TrainerListViewAdapter;
import com.imie.android.api.DataProvider;
import com.imie.android.api.PokemonWS;
import com.imie.android.api.TrainerWS;
import com.imie.android.model.Pokemon;
import com.imie.android.model.Trainer;
import com.imie.android.util.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.List;

import cz.msebera.android.httpclient.Header;
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
        setContentView(R.layout.pokedex);

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


    public void getPokemon(){

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
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


    /**
     * Method that performs RESTful webservice invocations
     *
     * @param pokemon
     *
    public void invokeWS(String pokemon) {

        /* Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();

        client.setTimeout(100000);
        client.get("http://10.0.2.2:8888/api/pokemon/" + pokemon, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {
                    if (responseBody != null) {
                        String str = new String(responseBody, StandardCharsets.UTF_8);

                        String pokemonName = "";
                        String pokemonTypeName = "";
                        String pokemonAttack1Name = "";
                        String pokemonAttack2Name = "";
                        String pokemonAttack3Name = "";
                        String pokemonAttack4Name = "";

                        try {
                            JSONObject json = new JSONObject(str);
                            name.setText("Nom: " + json.getString("name"));
                            name.setVisibility(View.VISIBLE);

                            JSONObject pokemonType = json.getJSONObject("pokemon_type");
                            pokemonTypeName = pokemonType.getString("name");
                            type.setText("Type: " + pokemonTypeName);
                            type.setVisibility(View.VISIBLE);

                            JSONObject pokemonAttack1 = json.getJSONObject("attack1");
                            pokemonAttack1Name = pokemonAttack1.getString("name");
                            attack1.setText("Attaque 1: " + pokemonAttack1Name);
                            attack1.setVisibility(View.VISIBLE);

                            JSONObject pokemonAttack2 = json.getJSONObject("attack2");
                            pokemonAttack2Name = pokemonAttack2.getString("name");
                            attack2.setText("Attaque 2: " + pokemonAttack2Name);
                            attack2.setVisibility(View.VISIBLE);

                            JSONObject pokemonAttack3 = json.getJSONObject("attack3");
                            pokemonAttack3Name = pokemonAttack3.getString("name");
                            attack3.setText("Attaque 3: " + pokemonAttack3Name);
                            attack3.setVisibility(View.VISIBLE);

                            JSONObject pokemonAttack4 = json.getJSONObject("attack4");
                            pokemonAttack4Name = pokemonAttack4.getString("name");
                            attack4.setText("Attaque 4: " + pokemonAttack4Name);
                            attack4.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Pokemon introuvable", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    */
}
