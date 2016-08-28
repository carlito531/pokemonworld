package com.imie.android;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.Text;
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
    private Pokemon trainer1Pokemon;
    private Pokemon trainer2Pokemon;
    private Boolean hasAlreadyAttacked;

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
                if (checkPokemonsList.equals(FightConstant.TRAINER1_POKEMONS_READY) && currentTrainer.equals(fight.getTrainer1().getLogin()) ||
                    checkPokemonsList.equals(FightConstant.TRAINER2_POKEMONS_READY) && currentTrainer.equals(fight.getTrainer2().getLogin())) {
                    TextView tvOpponentPokemonsNotReady = new TextView(getActivity());
                    tvOpponentPokemonsNotReady.setText("Les pokémons du joueur adverses ne sont pas prêts");
                    l.addView(tvOpponentPokemonsNotReady);


                // If both users are ready
                } else if (checkPokemonsList.equals(FightConstant.ALL_POKEMONS_READY)) {
                    TextView tvPokemonChoose = new TextView(getActivity());
                    tvPokemonChoose.setText("Choix du pokemon");
                    l.addView(tvPokemonChoose);

                    // Allow trainer to choose which pokemon will fight
                    feedPokemonChoiceView(fight, l);

                // If current trainer had not set is pokemon to fight
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

            // If both trainer have a pokemon ready to fight we can start the fight
            } else if (fight.getFight_state().getName().equals(FightConstant.FIGHT_CAN_START) ||
                    fight.getFight_state().getName().equals(FightConstant.TRAINER1_ATTACK_TURN) ||
                    fight.getFight_state().getName().equals(FightConstant.TRAINER2_ATTACK_TURN)) {

                // Get both trainers pokemon in fight
                String trainersPokemonList = checkTrainersPokemonList(fight);

                if (trainersPokemonList.equals(FightConstant.TRAINER1_POKEMONS_KO) ||
                    trainersPokemonList.equals(FightConstant.TRAINER2_POKEMONS_KO)) {

                    if (trainersPokemonList.equals(FightConstant.TRAINER1_POKEMONS_KO) && isTrainer1(fight, currentTrainer) ||
                        trainersPokemonList.equals(FightConstant.TRAINER2_POKEMONS_KO) && isTrainer1(fight, currentTrainer) == false) {
                        TextView tvLoose = new TextView(getActivity());
                        tvLoose.setText("Tous vos pokemons sont KO, vous avez perdu...");
                        l.addView(tvLoose);
                    } else {
                        TextView tvWin = new TextView(getActivity());
                        tvWin.setText("Tous les pokemons du joueur adverse sont KO, vous avez gagné !");
                        l.addView(tvWin);
                    }
                } else {
                    // If current user have another pokemon to choose
                    if (getBothTrainersPokemonInFight(fight).equals(FightConstant.WAITING_FOR_TRAINER1_POKEMON) ||
                            getBothTrainersPokemonInFight(fight).equals(FightConstant.WAITING_FOR_TRAINER2_POKEMON)) {

                        if (getBothTrainersPokemonInFight(fight).equals(FightConstant.WAITING_FOR_TRAINER1_POKEMON) && isTrainer1(fight, currentTrainer) ||
                                getBothTrainersPokemonInFight(fight).equals(FightConstant.WAITING_FOR_TRAINER2_POKEMON) && isTrainer1(fight, currentTrainer) == false) {
                            feedPokemonChoiceView(fight, l);
                        } else {
                            TextView tvWait = new TextView(getActivity());
                            tvWait.setText("En attente que le joueur adverse sélectionne un nouveau pokémon");
                            l.addView(tvWait);
                        }

                    } else {
                        // Display fight view
                        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        RelativeLayout pokemonFightLayout = (RelativeLayout) inflater.inflate(R.layout.pokemon_fight_layout, l, false);
                        l.addView(pokemonFightLayout);

                        // Feed the fight layout of the current trainer
                        if (isTrainer1(fight, currentTrainer)) {
                            feedPokemonFightLayout(fight, trainer1Pokemon, l);
                        } else {
                            feedPokemonFightLayout(fight, trainer2Pokemon, l);
                        }
                    }
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
     * Display buttons allowing user to choose his Pokemon
     *
     * @param fight
     */
    private void feedPokemonChoiceView(final Fight fight, LinearLayout layout) {
        if (currentTrainer.equals(fight.getTrainer1().getLogin())) {
            if (!checkPokemonInFight(trainer1PokemonsInFight)) {
                for (Pokemon pokemon : trainer1PokemonsInFight) {
                    Button button = new Button(getActivity());
                    button.setText(pokemon.getName());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button text = (Button) v;
                            String pokemon = text.getText().toString();
                            setPokemonInFight(fight, trainer1PokemonsInFight, pokemon);

                            // If other trainer pokemon is set, set a new fight state
                            if (checkPokemonInFight(trainer2PokemonsInFight)) {
                                FightWSimpl fightWSimpl = new FightWSimpl(getActivity());
                                fightWSimpl.updateFightState(fight.getId(), FightConstant.FIGHT_CAN_START);
                            }
                        }
                    });
                    layout.addView(button);
                }
            }
        } else {
            if (!checkPokemonInFight(trainer2PokemonsInFight)) {
                for (Pokemon pokemon : trainer2PokemonsInFight) {
                    Button button = new Button(getActivity());
                    button.setText(pokemon.getName());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button text = (Button) v;
                            String pokemon = text.getText().toString();
                            setPokemonInFight(fight, trainer2PokemonsInFight, pokemon);

                            // If other trainer pokemon is set, set a new fight state
                            if (checkPokemonInFight(trainer1PokemonsInFight)) {
                                FightWSimpl fightWSimpl = new FightWSimpl(getActivity());
                                fightWSimpl.updateFightState(fight.getId(), FightConstant.FIGHT_CAN_START);
                            }
                        }
                    });
                    layout.addView(button);
                }
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
            if (pokemon.getPokemon_fight_state() != null && pokemon.getHp() > 0) {
                    trainer1PokemonsInFight.add(pokemon);
                    trainer1ListSet = true;
            }
        }

        for (Pokemon pokemon : trainer2Pokemons) {
            if (pokemon.getPokemon_fight_state() != null && pokemon.getHp() > 0) {
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
        } else if (trainer1ListSet == false || trainer2ListSet == false) {
            if (trainer1ListSet == false) {
                state = FightConstant.TRAINER1_POKEMONS_KO;
            } else {
                state = FightConstant.TRAINER2_POKEMONS_KO;
            }
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


            String opponentName = "";
            if (isTrainer1(fight, name)) {
                opponentName = fight.getTrainer2().getLogin();
            } else {
                opponentName = fight.getTrainer1().getLogin();
            }

            MobileEngagementWSimpl mobileEngagementWSimpl = new MobileEngagementWSimpl(getActivity(), FightConstant.POKEMON_IN_FIGHT);
            mobileEngagementWSimpl.sendNotification(opponentName);
        } else {
            Toast.makeText(getActivity(), "Ce pokémon n'a plus de vie :(", Toast.LENGTH_LONG).show();
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


    /**
     * Check if one pokemon is ready to fight
     *
     * @return
     */
    private Boolean checkPokemonInFight(List<Pokemon> inFightList) {
        Boolean trainerPokemonSet = false;

        // Check if pokemons list have pokemons ready to fight
        for (Pokemon pokemon : inFightList) {
            if (pokemon.getPokemon_fight_state().getName().equals(FightConstant.IN_FIGHT)) {
                trainerPokemonSet = true;
            }
        }
        return trainerPokemonSet;
    }


    /**
     * Get both trainers pokemon in fight
     *
     * @param fight
     */
    private String getBothTrainersPokemonInFight(Fight fight) {
        String state = "";
        Boolean pokemon1InFight = false;
        Boolean pokemon2InFight = false;

        List<Pokemon> trainer1Pokemons = fight.getTrainer1().getPokemon();
        List<Pokemon> trainer2Pokemons = fight.getTrainer2().getPokemon();

        for (Pokemon pokemon : trainer1Pokemons) {
            if (pokemon.getPokemon_fight_state() != null) {
                if (pokemon.getPokemon_fight_state().getName().equals(FightConstant.IN_FIGHT)) {
                    trainer1Pokemon = pokemon;
                    pokemon1InFight = true;
                }
            }
        }

        for (Pokemon pokemon : trainer2Pokemons) {
            if (pokemon.getPokemon_fight_state() != null) {
                if (pokemon.getPokemon_fight_state().getName().equals(FightConstant.IN_FIGHT)) {
                    trainer2Pokemon = pokemon;
                    pokemon2InFight = true;
                }
            }
        }

        if (pokemon1InFight == false) {
            state = FightConstant.WAITING_FOR_TRAINER1_POKEMON;
        } else if(pokemon2InFight == false) {
            state = FightConstant.WAITING_FOR_TRAINER2_POKEMON;
        }

        return state;
    }


    /**
     * Feed the pokemon fight layout with the pokemon in fight
     *
     * @param pokemon
     * @param layout
     */
    private void feedPokemonFightLayout(final Fight fight, final Pokemon pokemon, View layout) {

        // Get the ui components from layout
        ImageView opponentAvatar = (ImageView) layout.findViewById(R.id.ivPokemonFightOpponentAvatar);
        TextView opponentPokemonName = (TextView) layout.findViewById(R.id.tvPokemonFightOpponentName);
        TextView opponentPokemonHp = (TextView) layout.findViewById(R.id.tvPokemonFightOpponentHp);

        final TextView fightInformation = (TextView) layout.findViewById(R.id.tvPokemonFightTextInfo);

        ImageView avatar = (ImageView) layout.findViewById(R.id.ivPokemonFightAvatar);
        TextView pokemonName = (TextView) layout.findViewById(R.id.ivPokemonFightName);
        TextView pokemonHp = (TextView) layout.findViewById(R.id.ivPokemonFightHp);
        Button attack1 = (Button) layout.findViewById(R.id.btPokemonFightAttack1);
        Button attack2 = (Button) layout.findViewById(R.id.btPokemonFightAttack2);
        Button attack3 = (Button) layout.findViewById(R.id.btPokemonFightAttack3);
        Button attack4 = (Button) layout.findViewById(R.id.btPokemonFightAttack4);

        // Set pokemons datas
        if (pokemon.getName().equals(trainer1Pokemon.getName())) {
            opponentPokemonName.setText("Nom: " + trainer2Pokemon.getName());
            opponentPokemonHp.setText("Vie :" + trainer2Pokemon.getHp().toString());
            opponentAvatar.setImageResource(getActivity().getResources().getIdentifier(trainer2Pokemon.getName().toLowerCase(), "drawable", getActivity().getPackageName()));


            if (fight.getFight_state().getName().equals(FightConstant.TRAINER1_ATTACK_TURN) ||
                fight.getFight_state().getName().equals(FightConstant.FIGHT_CAN_START)) {
                fightInformation.setText("C'est à vous d'attaquer");
            } else {
                fightInformation.setText("C'est à votre adversaire d'attaquer");
            }

        } else {
            opponentPokemonName.setText("Name: " + trainer1Pokemon.getName());
            opponentPokemonHp.setText("Vie: " + trainer1Pokemon.getHp().toString());
            opponentAvatar.setImageResource(getActivity().getResources().getIdentifier(trainer1Pokemon.getName().toLowerCase(), "drawable", getActivity().getPackageName()));

            if (fight.getFight_state().getName().equals(FightConstant.TRAINER2_ATTACK_TURN) ||
                fight.getFight_state().getName().equals(FightConstant.FIGHT_CAN_START)) {
                fightInformation.setText("C'est à vous d'attaquer");
            } else {
                fightInformation.setText("C'est à votre adversaire d'attaquer");
            }
        }

        avatar.setImageResource(getActivity().getResources().getIdentifier(pokemon.getName().toLowerCase(), "drawable", getActivity().getPackageName()));
        pokemonName.setText("Name: " + pokemon.getName());
        pokemonHp.setText("Vie: " + pokemon.getHp().toString());
        attack1.setText(pokemon.getAttack1().getName());
        attack2.setText(pokemon.getAttack2().getName());
        attack3.setText(pokemon.getAttack3().getName());
        attack4.setText(pokemon.getAttack4().getName());

        /// Check if it is the current trainer turn to attack, if true let him attack
        if (fight.getFight_state().getName().equals(FightConstant.TRAINER1_ATTACK_TURN) && isTrainer1(fight, currentTrainer) ||
            fight.getFight_state().getName().equals(FightConstant.TRAINER2_ATTACK_TURN) && isTrainer1(fight, currentTrainer) == false ||
            fight.getFight_state().getName().equals(FightConstant.FIGHT_CAN_START)) {

            hasAlreadyAttacked = false;

            final PokemonWSimpl pokemonWSimpl = new PokemonWSimpl(getActivity());
            final FightWSimpl fightWSimpl = new FightWSimpl(getActivity());

            // Set buttons listener
            attack1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hasAlreadyAttacked == false) {
                        if (pokemon.getName().equals(trainer1Pokemon.getName())) {
                            pokemonWSimpl.attackOpponent(trainer2Pokemon.getId(), pokemon.getAttack1().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER2_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        } else {
                            pokemonWSimpl.attackOpponent(trainer1Pokemon.getId(), pokemon.getAttack1().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER1_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        }
                        hasAlreadyAttacked = true;
                    }
                }
            });

            attack2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hasAlreadyAttacked == false) {
                        if (pokemon.getName().equals(trainer1Pokemon.getName())) {
                            pokemonWSimpl.attackOpponent(trainer2Pokemon.getId(), pokemon.getAttack2().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER2_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        } else {
                            pokemonWSimpl.attackOpponent(trainer1Pokemon.getId(), pokemon.getAttack2().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER1_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        }
                        hasAlreadyAttacked = true;
                    }
                }
            });

            attack3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hasAlreadyAttacked == false) {
                        if (pokemon.getName().equals(trainer1Pokemon.getName())) {
                            pokemonWSimpl.attackOpponent(trainer2Pokemon.getId(), pokemon.getAttack3().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER2_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        } else {
                            pokemonWSimpl.attackOpponent(trainer1Pokemon.getId(), pokemon.getAttack3().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER1_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        }
                        hasAlreadyAttacked = true;
                    }
                }
            });

            attack4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pokemon.getName().equals(trainer1Pokemon.getName())) {
                        if (hasAlreadyAttacked == false) {
                            pokemonWSimpl.attackOpponent(trainer2Pokemon.getId(), pokemon.getAttack4().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER2_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        } else {
                            pokemonWSimpl.attackOpponent(trainer1Pokemon.getId(), pokemon.getAttack4().getDamage());
                            fightWSimpl.updateFightState(fight.getId(), FightConstant.TRAINER1_ATTACK_TURN);
                            fightInformation.setText("C'est à votre adversaire d'attaqué");
                        }
                        hasAlreadyAttacked = true;
                    }
                }
            });
        }
    }
}
