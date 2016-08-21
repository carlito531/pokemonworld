package com.imie.android.serviceWS;

import com.imie.android.model.Fight;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by charly on 15/08/2016.
 */
public interface MobileEngagementWS {

    @FormUrlEncoded
    @POST("/api/engagement/sendNotification")
    Call<String> sendNotification(@Field("deviceId") String deviceId, @Field("sender") String sender,
                                  @Field("fightState") String fightState);
}
