package com.imie.android.serviceWS;

import com.imie.android.model.Pokemon;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by charly on 05/08/2016.
 */
public interface PokemonWS {

    @GET("/api/pokemon/")
    Call<List<Pokemon>> getPokemonList();

    @GET("/api/pokemon/{name}")
    Call<Pokemon> getPokemon(@Path("name") String name);


}
