package com.imie.android;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.imie.android.ViewHelper.PokemonListViewAdapter;
import com.imie.android.common.FightConstant;
import com.imie.android.model.Fight;
import com.imie.android.model.FightState;
import com.imie.android.model.Pokemon;
import com.imie.android.serviceWS.FightWS;
import com.imie.android.serviceWS.FightWSimpl;
import com.imie.android.serviceWS.MobileEngagementWSimpl;
import com.imie.android.serviceWS.PokemonWSimpl;
import com.imie.android.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FightTrainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FightTrainerFragment extends Fragment {

    private String currentTrainer;
    private String opponentName;
    private View root;
    private List<Pokemon> trainer1PokemonsInFight;
    private List<Pokemon> trainer2PokemonsInFight;

    public FightTrainerFragment() {
    }

    /**
     *
     * @return A new instance of fragment FightTrainerFragment.
     */
    public static FightTrainerFragment newInstance() {
        FightTrainerFragment fragment = new FightTrainerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get opponentName from google maps marker in activity
        if (getArguments().getString("opponentName") != null) {
            opponentName = getArguments().getString("opponentName");
        }
        currentTrainer = Util.getSharedPreferences("userLogin", getActivity().getApplicationContext());

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(getActivity().getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Ask the api if the user connected is already in a fight
        FightWS service = retrofit.create(FightWS.class);

        Call<Fight> item = service.getFight(currentTrainer);
        item.enqueue(new Callback<Fight>() {
            @Override
            public void onResponse(Response<Fight> response, Retrofit retrofit) {
                if (response.body() != null) {
                    Fight fight = response.body();
                    constructView(fight);
                } else {
                    constructView(null);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        // Initialize root view and sub layout
        root = inflater.inflate(R.layout.fragment_fight_trainer, container, false);
        return root;
    }


    /**
     * Construct the view which depend on fight state
     */
    private void constructView(final Fight fight) {

        LinearLayout l = (LinearLayout) root.findViewById(R.id.fightContentLayout);

        // If current user is in fight
        if (fight != null) {
            TextView currentFightState = new TextView(getActivity());

            // Display information about the current fight
            if (currentTrainer.equals(fight.getTrainer1().getLogin())) {
                currentFightState.setText("Combat avec " + fight.getTrainer2().getName());
                l.addView(currentFightState);
            } else {
                currentFightState.setText("Combat avec " + fight.getTrainer1().getName());
                l.addView(currentFightState);
            }


            // If a fight request has been sent
            if (fight.getFight_state().getName().equals(FightConstant.FIGHT_REQUEST_SENT)) {
                TextView tvAlreadyinFight = new TextView(getActivity());

                // If current user is the one who engaged the fight
                if (currentTrainer.equals(fight.getTrainer1().getLogin())) {
                    tvAlreadyinFight.setText("Vous avez déja envoyé une requête de combat à " + fight.getTrainer2().getName());
                    l.addView(tvAlreadyinFight);

                } else {
                    tvAlreadyinFight.setText("Une requête de combat a été envoyé par " + fight.getTrainer1().getName());
                    Button answerFightRequest = new Button(getActivity());
                    answerFightRequest.setText("Accepter combat");
                    answerFightRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FightWSimpl fightWSimpl = new FightWSimpl(getActivity());
                            final Integer fightId = fight.getId();
                            fightWSimpl.updateFightState(fightId, FightConstant.FIGHT_REQUEST_ACCEPTED);

                            MobileEngagementWSimpl mobileEngagementWSimpl = new MobileEngagementWSimpl(getActivity(), FightConstant.FIGHT_REQUEST_ACCEPTED);
                            mobileEngagementWSimpl.sendNotification(opponentName);
                        }
                    });

                    l.addView(tvAlreadyinFight);
                    l.addView(answerFightRequest);
                }

            // If fight request is accepted, let the user choose 4 pokemons
            } else if (fight.getFight_state().getName().equals(FightConstant.FIGHT_REQUEST_ACCEPTED)) {

                // Check pokemon list state for each users
                String checkPokemonsList = checkTrainersPokemonList(fight);

                // If current user is ready
                if (checkPokemonsList.equals(FightConstant.TRAINER1_POKEMONS_READY) && currentTrainer.equals(fight.getTrainer1().getLogin())
                        || checkPokemonsList.equals(FightConstant.TRAINER2_POKEMONS_READY) && currentTrainer.equals(fight.getTrainer2().getLogin())) {
                    TextView tvOpponentPokemonsNotReady = new TextView(getActivity());
                    tvOpponentPokemonsNotReady.setText("Les pokémons du joueur adverses ne sont pas prêts");
                    l.addView(tvOpponentPokemonsNotReady);


                // If both users are ready
                } else if (checkPokemonsList.equals(FightConstant.ALL_POKEMONS_READY)) {
                    TextView tvPokemonChoose = new TextView(getActivity());
                    tvPokemonChoose.setText("Choix du pokemon");
                    l.addView(tvPokemonChoose);

                    if (isTrainer1(fight, currentTrainer)) {
                        for (Pokemon pokemon : trainer1PokemonsInFight) {
                            Button button = new Button(getActivity());
                            button.setText(pokemon.getName());
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Button text = (Button) v;
                                    String pokemon = text.getText().toString();
                                    setPokemonInFight(fight, trainer1PokemonsInFight, pokemon);
                                }
                            });
                            l.addView(button);
                        }
                    } else {
                        for (Pokemon pokemon : trainer2PokemonsInFight) {
                            Button button = new Button(getActivity());
                            button.setText(pokemon.getName());
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            l.addView(button);
                        }
                    }

                    //LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //RelativeLayout pokemonFightLayout = (RelativeLayout) inflater.inflate(R.layout.pokemon_fight_layout, l, false);
                    //l.addView(pokemonFightLayout);

                } else {
                    TextView tvFightAccepted = new TextView(getActivity());
                    tvFightAccepted.setText("La requête de combat a été accepté, choisissez 4 pokemons pour combattre");

                    Button startPokemonChoice = new Button(getActivity());
                    startPokemonChoice.setText("Choisir pokemons");
                    startPokemonChoice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Go to PokemonActivity to choose pokemons
                            Intent pokemonActivity = new Intent(getActivity(), PokemonActivity.class);
                            pokemonActivity.putExtra("fight", fight);
                            startActivity(pokemonActivity);
                        }
                    });

                    l.addView(tvFightAccepted);
                    l.addView(startPokemonChoice);
                }
            }

            // If current user is not in fight
        } else {
            TextView tvNoCurrentFight = new TextView(getActivity());
            tvNoCurrentFight.setText("Pas de combat en cours");
            l.addView(tvNoCurrentFight);

            if (opponentName != null) {
                Button engageFight = new Button(getActivity());
                engageFight.setText("Engager combat avec " + opponentName);
                engageFight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FightWSimpl fightWSimpl = new FightWSimpl(getActivity());
                        fightWSimpl.setNewFight(FightConstant.FIGHT_REQUEST_SENT, opponentName);

                        MobileEngagementWSimpl mobileEngagementWSimpl = new MobileEngagementWSimpl(getActivity(), FightConstant.FIGHT_REQUEST_SENT);
                        mobileEngagementWSimpl.sendNotification(opponentName);
                    }
                });

                l.addView(engageFight);
            }
        }
    }


    /**
     * Check the pokemons list for each trainer
     *
     * @param fight
     * @return
     */
    private String checkTrainersPokemonList(Fight fight) {
        String state = "";

        trainer1PokemonsInFight = new ArrayList<Pokemon>();
        trainer2PokemonsInFight = new ArrayList<Pokemon>();

        // Get lists
        List<Pokemon> trainer1Pokemons = fight.getTrainer1().getPokemon();
        List<Pokemon> trainer2Pokemons = fight.getTrainer2().getPokemon();

        Boolean trainer1ListSet = false;
        Boolean trainer2ListSet = false;

        // Check if pokemons list have pokemons ready to fight
        for (Pokemon pokemon : trainer1Pokemons) {
            if (pokemon.getPokemon_fight_state() != null) {
                trainer1PokemonsInFight.add(pokemon);
                trainer1ListSet = true;
            }
        }

        for (Pokemon pokemon : trainer2Pokemons) {
            if (pokemon.getPokemon_fight_state() != null) {
                trainer2PokemonsInFight.add(pokemon);
                trainer2ListSet = true;
            }
        }

        // Check result
        if (trainer1ListSet && trainer2ListSet) {
            state = FightConstant.ALL_POKEMONS_READY;
        } else if (trainer1ListSet) {
            state = FightConstant.TRAINER1_POKEMONS_READY;
        } else if (trainer2ListSet) {
            state = FightConstant.TRAINER2_POKEMONS_READY;
        }

        return state;
    }


    /**
     * Check who is trainer1
     */
    private boolean isTrainer1(Fight fight, String name) {
        if (name.equals(fight.getTrainer1().getLogin())) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Set pokemon choosen state "in fight"
     */
    private void setPokemonInFight(Fight fight, List<Pokemon> pokemons, String name) {

        Pokemon toPutInFight = null;

        // Map pokemon cliked with pokemon in list
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getName().equals(name)) {
                toPutInFight = pokemon;
            }
        }

        // Check Pokemon health
        if (isOkForFight(toPutInFight)) {
            PokemonWSimpl pokemonWSimpl = new PokemonWSimpl(getActivity());
            pokemonWSimpl.putPokemonInFight(toPutInFight.getId());

            FightWSimpl fightWSimpl = new FightWSimpl(getActivity());
            //fightWSimpl.updateFightState(fight, FightConstant.);
        }
    }

    /**
     * Check if pokemon have enough health for fight
     */
    Boolean isOkForFight(Pokemon pokemon) {
        if (pokemon.getHp() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
