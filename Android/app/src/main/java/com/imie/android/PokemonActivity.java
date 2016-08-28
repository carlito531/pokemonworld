package com.imie.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.imie.android.ViewHelper.PokemonListViewAdapter;
import com.imie.android.common.FightConstant;
import com.imie.android.model.Fight;
import com.imie.android.model.Trainer;
import com.imie.android.serviceWS.DataProvider;
import com.imie.android.serviceWS.FightWS;
import com.imie.android.serviceWS.FightWSimpl;
import com.imie.android.serviceWS.MobileEngagementWS;
import com.imie.android.serviceWS.MobileEngagementWSimpl;
import com.imie.android.serviceWS.PokemonWS;
import com.imie.android.model.Pokemon;
import com.imie.android.serviceWS.PokemonWSimpl;
import com.imie.android.serviceWS.TrainerWS;
import com.imie.android.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PokemonActivity extends AppCompatActivity {

    private ListView pokemonList;
    private List<Integer> pokemonsChoosenIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        pokemonList = (ListView) findViewById(R.id.lvPokemons);


        // If the access is from the fightActivity
        Intent fromFightActivity = getIntent();
        final Fight fight = (Fight)fromFightActivity.getSerializableExtra("fight");

        if (fight != null) {
            pokemonsChoosenIds = new ArrayList<Integer>();

            // Let the user choose his pokemons to fight
            pokemonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pokemon pokemon = (Pokemon) pokemonList.getItemAtPosition(position);

                    if (pokemonsChoosenIds.size() < 4) {
                        if(!pokemonsChoosenIds.contains(pokemon.getId())) {
                            pokemonsChoosenIds.add(pokemon.getId());
                            Toast.makeText(getApplicationContext(), pokemon.getName() + " ajouté à la liste", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), pokemon.getName() + " déja dans la liste", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        JSONArray ids = new JSONArray();
                        for (int i = 0; i < pokemonsChoosenIds.size(); i++) {
                            ids.put(pokemonsChoosenIds.get(i));
                        }

                        // Update the pokemons fight states in database
                        PokemonWSimpl pokemonWSimpl = new PokemonWSimpl(getApplicationContext());
                        pokemonWSimpl.putPokemonsInFightList(ids);

                        // Get the opponentName
                        String opponentName = "";
                        if (Util.getSharedPreferences("userLogin", getApplicationContext()).equals(fight.getTrainer1().getLogin())) {
                            opponentName = fight.getTrainer2().getLogin();
                            MobileEngagementWSimpl mobileEngagementWSimpl = new MobileEngagementWSimpl(getApplicationContext(), FightConstant.TRAINER1_POKEMONS_READY);
                            mobileEngagementWSimpl.sendNotification(opponentName);

                        } else {
                            opponentName = fight.getTrainer1().getLogin();
                            MobileEngagementWSimpl mobileEngagementWSimpl = new MobileEngagementWSimpl(getApplicationContext(), FightConstant.TRAINER2_POKEMONS_READY);
                            mobileEngagementWSimpl.sendNotification(opponentName);
                        }

                        Intent fightActivity = new Intent(getApplicationContext(), FightActivity.class);
                        startActivity(fightActivity);
                    }
                }
            });
        }

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Get connected user login
        String login = Util.getSharedPreferences("userLogin", getApplicationContext());

        // Get pokemon list by calling the symfony api
        TrainerWS trainerService = retrofit.create(TrainerWS.class);

        Call<Trainer> pokemonItems = trainerService.getTrainer(login);
        pokemonItems.enqueue(new Callback<Trainer>() {
            @Override
            public void onResponse(Response<Trainer> response, Retrofit retrofit) {
                DataProvider.getInstance().setItem(response.body());

                Trainer trainer = null;

                if(response.body() != null) {
                    trainer = response.body();
                }

                // fill the pokemon listView component with the response
                try {
                    PokemonListViewAdapter adapter = new PokemonListViewAdapter(PokemonActivity.this, trainer.getPokemon());
                    pokemonList.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
