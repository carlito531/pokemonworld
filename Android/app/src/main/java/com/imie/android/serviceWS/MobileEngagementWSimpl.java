package com.imie.android.serviceWS;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.imie.android.common.FightConstant;
import com.imie.android.model.Fight;
import com.imie.android.model.Trainer;
import com.imie.android.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by charly on 15/08/2016.
 */
public class MobileEngagementWSimpl {

    private Retrofit retrofit;
    private Context context;
    private String fightState;

    public MobileEngagementWSimpl(Context context, String opponentName, String fightState) {
        this.context = context;
        this.fightState = fightState;

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     * Send fight engagement notification
     */
    public void sendNotification(String opponentName) {

        // Get device id
        TrainerWS trainerWS = retrofit.create(TrainerWS.class);
        Call<Trainer> trainerItem = trainerWS.getTrainer(opponentName);
        trainerItem.enqueue(new Callback<Trainer>() {
            @Override
            public void onResponse(Response<Trainer> response, Retrofit retrofit) {

                // Send the notification
                String sender = Util.getSharedPreferences("userLogin", context);
                MobileEngagementWS service = retrofit.create(MobileEngagementWS.class);
                Call<String> item = service.sendNotification(response.body().getDevice_id(), sender, fightState);
                item.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        if (response.body() != null) {
                            Toast.makeText(context, "Engager combat !", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
