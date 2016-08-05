package com.imie.android.api;

import android.view.View;
import android.widget.Toast;

import com.imie.android.model.Pokemon;
import com.imie.android.util.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by charly on 31/07/2016.
 */
public class PokemonApiHelper extends AbstractApiHelper {

    private String pokemonName = null;
    private String pokemonTypeName = null;
    private String pokemonLevel = null;
    private String pokemonExperience = null;
    private String pokemonHp = null;
    private String pokemonAttack1Name = null;
    private String pokemonAttack2Name = null;
    private String pokemonAttack3Name = null;
    private String pokemonAttack4Name = null;

    public PokemonApiHelper() {
        super();
    }

    /**
     * Call the api to get all the pokemons from database
     *
     */
    public List<Pokemon> getPokemons() {

        List<Pokemon> pokemons = new ArrayList<Pokemon>();
        JSONArray result = null;
        JSONObject currentObject = null;

        try {
            this.findAll();
            result = this.getResultList();

            for (int i = 0; i < result.length(); i++) {

                // get json informations about the pokemons
                if (currentObject.has("name")) {
                    this.pokemonName = currentObject.getString("name");

                } else if (currentObject.has("level")) {
                    this.pokemonLevel = currentObject.getString("level");

                } else if (currentObject.has("experience")) {
                    this.pokemonExperience = currentObject.getString("experience");

                } else if (currentObject.has("hp")) {
                    this.pokemonHp = currentObject.getString("hp");

                } else if (currentObject.has("pokemon_type")) {
                    JSONObject pokemonType = currentObject.getJSONObject("pokemon_type");
                    this.pokemonTypeName = currentObject.getString("name");

                } else if (currentObject.has("attack1")) {
                    JSONObject pokemonAttack1 = currentObject.getJSONObject("attack1");
                    this.pokemonAttack1Name = currentObject.getString("name");

                } else if (currentObject.has("attack2")) {
                    JSONObject pokemomAttack2 = currentObject.getJSONObject("attack2");
                    this.pokemonAttack2Name = currentObject.getString("name");

                } else if (currentObject.has("attack3")) {
                    JSONObject pokemomAttack3 = currentObject.getJSONObject("attack3");
                    this.pokemonAttack3Name = currentObject.getString("name");

                } else if (currentObject.has("attack4")) {
                    JSONObject pokemomAttack4 = currentObject.getJSONObject("attack4");
                    this.pokemonAttack4Name = currentObject.getString("name");
                }


                // add pokemon to list
                if (this.pokemonName != null && this.pokemonLevel != null && this.pokemonExperience != null
                        && this.pokemonHp != null && this.pokemonTypeName != null && this.pokemonAttack1Name != null
                        && this.pokemonAttack2Name != null && this.pokemonAttack3Name != null && this.pokemonAttack4Name != null) {

                    Pokemon pokemon = new Pokemon(this.pokemonName, this.pokemonAttack1Name, this.pokemonAttack2Name,
                            this.pokemonAttack3Name, this.pokemonAttack4Name, this.pokemonTypeName, Integer.parseInt(this.pokemonLevel),
                            Integer.parseInt(this.pokemonExperience), Integer.parseInt(this.pokemonHp));

                    pokemons.add(pokemon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pokemons;
    }

    public String getRoute() {
        return "pokemon/";
    }
}
