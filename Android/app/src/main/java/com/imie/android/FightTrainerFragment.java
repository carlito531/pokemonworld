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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.imie.android.common.FightConstant;
import com.imie.android.model.Fight;
import com.imie.android.model.FightState;
import com.imie.android.serviceWS.FightWS;
import com.imie.android.serviceWS.FightWSimpl;
import com.imie.android.serviceWS.MobileEngagementWSimpl;
import com.imie.android.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
     * Construct the view depend on fight state
     */
    private void constructView(final Fight fight) {

        LinearLayout l = (LinearLayout) root.findViewById(R.id.fightContentLayout);

        // If current user is in fight
        if (fight != null) {
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

                            MobileEngagementWSimpl mobileEngagementWSimpl = new MobileEngagementWSimpl(getActivity(), opponentName, FightConstant.FIGHT_REQUEST_ACCEPTED);
                            mobileEngagementWSimpl.sendNotification(opponentName);
                        }
                    });

                    l.addView(tvAlreadyinFight);
                    l.addView(answerFightRequest);
                }

            // If fight request is accepted, let the user choose 4 pokemons
            } else if (fight.getFight_state().getName().equals(FightConstant.FIGHT_REQUEST_ACCEPTED)) {
                TextView tvFightAccepted = new TextView(getActivity());
                tvFightAccepted.setText("La requête de combat a été accepté, choisissez 4 pokemons pour combattre");

                Button startPokemonChoice = new Button(getActivity());
                startPokemonChoice.setText("Choisir pokemons");
                startPokemonChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Go to PokemonActivity to choose pokemons
                        Intent pokemonActivity = new Intent(getActivity(), PokemonActivity.class);
                        pokemonActivity.putExtra("pokemonChoice","pokemonChoice");
                        startActivity(pokemonActivity);
                    }
                });

                l.addView(tvFightAccepted);
                l.addView(startPokemonChoice);
            }


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

                        MobileEngagementWSimpl mobileEngagementWSimpl = new MobileEngagementWSimpl(getActivity(), opponentName, FightConstant.FIGHT_REQUEST_SENT);
                        mobileEngagementWSimpl.sendNotification(opponentName);
                    }
                });

                l.addView(engageFight);
            }
        }
    }
}
