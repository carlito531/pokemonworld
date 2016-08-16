package com.imie.android;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imie.android.common.FightConstant;
import com.imie.android.serviceWS.FightWSImpl;
import com.imie.android.serviceWS.TrainerWS;
import com.imie.android.model.Trainer;
import com.imie.android.util.Util;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class FightActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TabHost tabHost;
    private Retrofit retrofit;
    private GoogleMap maps;
    private LinearLayout dresseurLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Initialize google maps fragment
        MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        // initialize dresseur layout
        dresseurLayout = (LinearLayout) findViewById(R.id.Dresseurs);

        // set navigation on tabs
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Map");
        spec.setContent(R.id.tabMap);
        spec.setIndicator("Map");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Dresseurs");
        spec.setContent(R.id.Dresseurs);
        spec.setIndicator("Dresseurs");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Pokemons");
        spec.setContent(R.id.Pokemons);
        spec.setIndicator("Pokemons");
        tabHost.addTab(spec);
    }


    /**
     * Actions when Google map is ready
     * @param googleMap
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        maps = googleMap;

        maps.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // if user clicked on his marker
                String connectedUser = Util.getSharedPreferences("userLogin", getApplicationContext());
                if (marker.getTitle().equals(connectedUser)) {
                    Intent intent = new Intent(FightActivity.this, TrainerActivity.class);
                    startActivity(intent);

                } else {
                    updateDresseurLayout(marker);
                    tabHost.setCurrentTab(1);
                }
            }
        });

        getUserPosition();
        getUsersPosition();
    }


    /**
     * Get user position to place his marker on the Google maps
     */
    public void getUserPosition() {

        // Get user login which is stored in sharedPreferences
        String login = Util.getSharedPreferences("userLogin", getApplicationContext());

        // Call webservice to get the user position
        if (!login.equals("")) {

            TrainerWS service = retrofit.create(TrainerWS.class);
            Call<Trainer> item = service.getTrainer(login);

            item.enqueue(new Callback<Trainer>() {
                @Override
                public void onResponse(Response<Trainer> response, Retrofit retrofit) {
                    if (response.body() != null) {
                        Trainer trainer = response.body();

                        LatLng trainerLatLng = new LatLng(trainer.getPosition().getLatitude(), trainer.getPosition().getLongitude());
                        maps.addMarker(new MarkerOptions().position(trainerLatLng).title(trainer.getLogin()));
                        maps.moveCamera(CameraUpdateFactory.newLatLng(trainerLatLng));
                        maps.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }


    /**
     * Get all trainers positions and add a marker for each ones on the google map
     */
    public void getUsersPosition() {

        TrainerWS service = retrofit.create(TrainerWS.class);
        Call<List<Trainer>> item = service.getTrainerList();

        item.enqueue(new Callback<List<Trainer>>() {
            @Override
            public void onResponse(Response<List<Trainer>> response, Retrofit retrofit) {
                if (response.body() != null) {

                    List<Trainer> trainers = response.body();

                    String currentUser = Util.getSharedPreferences("userLogin", getApplicationContext());

                    // for each trainers except the connected user, put a marker on the map
                    for (Trainer trainer : trainers) {
                        if (!trainer.getLogin().equals(currentUser)) {
                            LatLng trainerLatLng = new LatLng(trainer.getPosition().getLatitude(), trainer.getPosition().getLongitude());
                            maps.addMarker(new MarkerOptions()
                                    .position(trainerLatLng)
                                    .title("<dresseur> " + trainer.getLogin())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /**
     * on tabHost switched, construct the view
     */
    public void updateDresseurLayout(final Marker marker) {

        // clean layout
        dresseurLayout.removeAllViews();

        // if user clicked on an opponent marker
        if (marker.getTitle().contains("<dresseur>")) {
            LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View trainerActionView = layoutInflater.inflate(R.layout.map_trainer_action, null);
            dresseurLayout.addView(trainerActionView);

            ImageView ivOpponentAvatar = (ImageView) trainerActionView.findViewById(R.id.ivOpponentAvatar);
            ivOpponentAvatar.setImageDrawable(new ColorDrawable(Color.BLACK));

            TextView tvName = (TextView) trainerActionView.findViewById(R.id.tvOpponentName);
            tvName.setText(marker.getTitle());

            Button btnEngage = (Button)trainerActionView.findViewById(R.id.btnOpponentEngage);
            btnEngage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String opponentName = Util.parseMarkerTitle(marker.getTitle());
                    FightWSImpl fightWSimpl = new FightWSImpl(opponentName, getApplicationContext(), FightConstant.SENDING_REQUEST);
                }
            });
        }

        // if user clicked on a pokemon

        // if user clicked on a npc
    }
}
