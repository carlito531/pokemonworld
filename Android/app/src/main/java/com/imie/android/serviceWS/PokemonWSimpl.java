package com.imie.android.serviceWS;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.imie.android.FightActivity;
import com.imie.android.PokemonActivity;
import com.imie.android.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by charly on 23/08/2016.
 */
public class PokemonWSimpl {

    private Context context;
    private Retrofit retrofit;

    public PokemonWSimpl(Context context) {
        this.context = context;

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
            .baseUrl(Util.getApiUrlBase(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }


    /**
     * Put pokemons in fight list
     * @param ids
     */
    public void putPokemonsInFightList(JSONArray ids) {

        PokemonWS pokemonWS = retrofit.create(PokemonWS.class);
        Call<String> item = pokemonWS.putInFightList(ids);

        item.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Put a pokemon "in fight" state
     *
     * @param id
     */
    public void putPokemonInFight(Integer id) {
        PokemonWS pokemonWS = retrofit.create(PokemonWS.class);
        Call<String> item = pokemonWS.putInFight(id);

        item.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Attack opponent pokemon
     *
     * @param opponentId
     * @param damage
     */
    public void attackOpponent(Integer opponentId, Integer damage) {
        PokemonWS pokemonWS = retrofit.create(PokemonWS.class);
        Call<String> item = pokemonWS.attackOpponent(opponentId, damage);

        item.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
