package com.imie.android.serviceWS;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by charly on 15/08/2016.
 */
public interface FightWS {

    @FormUrlEncoded
    @POST("/api/fight/sendFightEngagementNotification")
    Call<String> sendFightEngagementNotif(@Field("deviceId") String deviceId);
}
