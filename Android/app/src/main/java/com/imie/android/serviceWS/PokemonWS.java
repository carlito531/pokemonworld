package com.imie.android.serviceWS;

import com.imie.android.model.Pokemon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by charly on 05/08/2016.
 */
public interface PokemonWS {

    @GET("/api/pokemon/")
    Call<List<Pokemon>> getPokemonList();

    @GET("/api/pokemon/{name}")
    Call<Pokemon> getPokemon(@Path("name") String name);

    @FormUrlEncoded
    @PUT("/api/pokemon/putInFightList")
    Call<String> putInFightList(@Field("pokemonIds") JSONArray ids);

    @PUT("/api/pokemon/{id}/putInFight")
    Call<String> putInFight(@Path("id") Integer id);

    @FormUrlEncoded
    @PUT("/api/pokemon/{id}/attack")
    Call<String> attackOpponent(@Path("id") Integer id, @Field("damage") Integer damage);
}
