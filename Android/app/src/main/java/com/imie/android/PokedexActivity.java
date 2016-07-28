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

import com.imie.android.util.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

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
                getPokemonInformations(v);
            }
        });
    }

    /**
     *
     *
     * @param view
     */
    public void getPokemonInformations(View view){

        String name = nameSearch.getText().toString();

        // Instantiate Http Request Param Object
        // RequestParams params = new RequestParams();
        // When Email Edit View and Password Edit View have values other than Null
        if (name != null) {
            invokeWS(name);
        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param pokemon
     */
    public void invokeWS(String pokemon) {

        // Make RESTful webservice call using AsyncHttpClient object
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
}
