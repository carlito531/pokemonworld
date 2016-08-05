package com.imie.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;

import com.imie.android.ViewHelper.PokemonListViewAdapter;
import com.imie.android.api.PokemonApiHelper;
import com.imie.android.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class FightActivity extends AppCompatActivity {

    private TabHost tabHost;
    private ListView pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        // fill the pokemon list view
        pokemonList = (ListView) findViewById(R.id.lvPokemons);
        List<Pokemon> pokemons = generatePokemonListView();
        PokemonListViewAdapter adapter = new PokemonListViewAdapter(FightActivity.this, pokemons);
        pokemonList.setAdapter(adapter);

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
    }

    private List<Pokemon> generatePokemonListView(){

        PokemonApiHelper pokemonHelper = new PokemonApiHelper();
        List<Pokemon> pokemons = pokemonHelper.getPokemons();

        /*
        tweets.add(new Tweet(Color.BLACK, "Florent", "Mon premier tweet !"));
        tweets.add(new Tweet(Color.BLUE, "Kevin", "C'est ici que ça se passe !"));
        tweets.add(new Tweet(Color.GREEN, "Logan", "Que c'est beau..."));
        tweets.add(new Tweet(Color.RED, "Mathieu", "Il est quelle heure ??"));
        tweets.add(new Tweet(Color.GRAY, "Willy", "On y est presque"));
        */
        return pokemons;
    }
}
