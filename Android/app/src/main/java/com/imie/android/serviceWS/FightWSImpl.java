package com.imie.android.serviceWS;

import android.content.Context;
import android.widget.Toast;

import com.imie.android.model.Fight;
import com.imie.android.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.Path;

/**
 * Created by charly on 21/08/2016.
 */
public class FightWSimpl {

    private Context context;
    private Retrofit retrofit;

    public FightWSimpl(Context context) {
        this.context = context;

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     * Initialize a new fight in database
     *
     * @param fightState
     * @param opponentName
     */
    public void setNewFight(String fightState, String opponentName) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime()).toString();
        String trainer1 = Util.getSharedPreferences("userLogin", context);

        FightWS fightWS = retrofit.create(FightWS.class);
        Call<String> fightItem = fightWS.setNewFight(date, fightState, trainer1, opponentName);
        fightItem.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                Toast.makeText(context, response.body(), Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * Update fightState
     *
     * @param fightId
     * @param fightState
     */
    public void updateFightState(Integer fightId, String fightState) {

        FightWS fightWS = retrofit.create(FightWS.class);
        Call<String> fightItem = fightWS.updateFightState(fightId, fightState);
        fightItem.enqueue(new Callback<String>() {
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
