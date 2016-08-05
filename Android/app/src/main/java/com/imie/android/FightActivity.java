package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.imie.android.ViewHelper.PokemonListViewAdapter;
import com.imie.android.api.DataProvider;
import com.imie.android.api.PokemonListWS;
import com.imie.android.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class FightActivity extends AppCompatActivity {

    private TabHost tabHost;
    private ListView pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        // fill the pokemon list view
        pokemonList = (ListView) findViewById(R.id.lvPokemons);

        // set navigation on tabs
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Dresseurs");
        spec.setContent(R.id.Dresseurs);
        spec.setIndicator("Dresseurs");
        tabHost.addTab(spec);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        spec = tabHost.newTabSpec("Map");
        spec.setContent(R.id.Map);
        spec.setIndicator("Map");
        tabHost.addTab(spec);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        spec = tabHost.newTabSpec("Pokemons");
        spec.setContent(R.id.Pokemons);
        spec.setIndicator("Pokemons");
        tabHost.addTab(spec);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonListWS service = retrofit.create(PokemonListWS.class);

        Call<List<Pokemon>> items = service.listTestItem();
        items.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Response<List<Pokemon>> response, Retrofit retrofit) {
                DataProvider.getInstance().setItems(response.body());
                Toast.makeText(getApplication(),"Success", Toast.LENGTH_SHORT).show();

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
}
