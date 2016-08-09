package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imie.android.ViewHelper.PokemonListViewAdapter;
import com.imie.android.ViewHelper.TrainerListViewAdapter;
import com.imie.android.api.DataProvider;
import com.imie.android.api.PokemonWS;
import com.imie.android.api.TrainerWS;
import com.imie.android.model.Pokemon;
import com.imie.android.model.Position;
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
    private ListView trainerList;
    private ListView pokemonList;
    private Retrofit retrofit;
    private GoogleMap maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        // Initialize Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Get listViews
        trainerList = (ListView) findViewById(R.id.lvTrainers);
        pokemonList = (ListView) findViewById(R.id.lvPokemons);

        // Initialize google maps fragment
        MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        // set navigation on tabs
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Dresseurs");
        spec.setContent(R.id.Dresseurs);
        spec.setIndicator("Dresseurs");
        tabHost.addTab(spec);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        spec = tabHost.newTabSpec("Resumé");
        spec.setContent(R.id.Resume);
        spec.setIndicator("Resumé");
        tabHost.addTab(spec);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        spec = tabHost.newTabSpec("Pokemons");
        spec.setContent(R.id.Pokemons);
        spec.setIndicator("Pokemons");
        tabHost.addTab(spec);

        // Get trainer list by calling the symfony api
        TrainerWS trainerService = retrofit.create(TrainerWS.class);

        Call<List<Trainer>> trainerItems = trainerService.getTrainerList();
        trainerItems.enqueue(new Callback<List<Trainer>>() {
            @Override
            public void onResponse(Response<List<Trainer>> response, Retrofit retrofit) {
                DataProvider.getInstance().setItems(response.body());

                // fill the pokemon listView component with the response
                try {
                    TrainerListViewAdapter adapter = new TrainerListViewAdapter(FightActivity.this, response.body());
                    trainerList.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(),"Error", Toast.LENGTH_SHORT).show();
            }
        });


        // Get pokemon list by calling the symfony api
        PokemonWS pokemonService = retrofit.create(PokemonWS.class);

        Call<List<Pokemon>> pokemonItems = pokemonService.getPokemonList();
        pokemonItems.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Response<List<Pokemon>> response, Retrofit retrofit) {
                DataProvider.getInstance().setItems(response.body());

                // fill the pokemon listView component with the response
                try {
                    PokemonListViewAdapter adapter = new PokemonListViewAdapter(FightActivity.this, response.body());
                    pokemonList.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(),"Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Get user position to place his marker on the Google maps
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
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps = googleMap;
        getUserPosition();
    }
}
