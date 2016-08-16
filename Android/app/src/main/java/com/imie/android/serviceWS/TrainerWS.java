package com.imie.android.serviceWS;

import com.imie.android.model.Position;
import com.imie.android.model.Trainer;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by charly on 05/08/2016.
 */
public interface TrainerWS {

    @GET("/api/trainer/")
    Call<List<Trainer>> getTrainerList();

    @GET("/api/trainer/{name}")
    Call<Trainer> getTrainer(@Path("name") String name);

    @FormUrlEncoded
    @POST("/api/connection/")
    Call<String> getConnection(@Field("login") String login, @Field("password") String password, @Field("pseudo") String pseudo, @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @PUT("/api/trainer/{name}/move")
    Call<String> updateTrainerPosition(@Path("name") String name, @Field("latitude") Float latitude, @Field("longitude") Float longitude);

}
