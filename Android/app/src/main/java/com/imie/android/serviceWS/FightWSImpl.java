package com.imie.android.serviceWS;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.imie.android.model.Trainer;
import com.imie.android.util.Util;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by charly on 15/08/2016.
 */
public class FightWSImpl {

    private String opponentName;
    private String opponentDeviceId;
    private String fightState;
    private Retrofit retrofit;
    private Context context;

    public FightWSImpl(String opponent, Context context, String fightState) {
        this.opponentName = opponent;
        this.context = context;
        this.fightState = fightState;

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get opponentDeviceId
        getOpponentDeviceId();

        // Send notification to opponent
        sendFightEngagementNotification();
    }

    /**
     * Get opponentDeviceId
     */
    private void getOpponentDeviceId() {

        TrainerWS service = retrofit.create(TrainerWS.class);

        Call<Trainer> item = service.getTrainer(opponentName);
        item.enqueue(new Callback<Trainer>() {
            @Override
            public void onResponse(Response<Trainer> response, Retrofit retrofit) {
                if (response.body() != null) {
                    opponentDeviceId = response.body().getDevice_id();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Send fight engagement notification
     */
    private void sendFightEngagementNotification() {

        FightWS service = retrofit.create(FightWS.class);

        Call<String> item = service.sendFightEngagementNotif(opponentDeviceId);
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

}
