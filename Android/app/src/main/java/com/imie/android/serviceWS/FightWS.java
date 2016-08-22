package com.imie.android.serviceWS;

import com.imie.android.model.Fight;
import com.imie.android.model.Trainer;

import java.util.Date;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by charly on 15/08/2016.
 */
public interface FightWS {

    @FormUrlEncoded
    @POST("/api/fight/new")
    Call<String> setNewFight(@Field("date") String date, @Field("fightState") String fightState, @Field("trainer1") String trainer1, @Field("trainer2") String trainer2);

    @GET("/api/fight/{trainer}")
    Call<Fight> getFight(@Path("trainer") String trainer);

    @FormUrlEncoded
    @PUT("/api/fight/{id}/update")
    Call<String> updateFightState(@Path("id") Integer id, @Field("fightState") String fightState);
}
