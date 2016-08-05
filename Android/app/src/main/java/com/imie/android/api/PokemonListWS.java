package com.imie.android.api;

import com.imie.android.model.Pokemon;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by charly on 05/08/2016.
 */
public interface PokemonListWS {

    @GET("/api/pokemon/")
    Call<List<Pokemon>> listTestItem();

}
