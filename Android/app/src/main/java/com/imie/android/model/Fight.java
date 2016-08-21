package com.imie.android.model;

import java.util.Date;

/**
 * Created by charly on 16/08/2016.
 */
public class Fight {

    private String date;
    private FightState fight_state;
    private Trainer trainer1;
    private Trainer trainer2;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FightState getFight_state() {
        return fight_state;
    }

    public void setFight_state(FightState fight_state) {
        this.fight_state = fight_state;
    }

    public Trainer getTrainer1() {
        return trainer1;
    }

    public void setTrainer1(Trainer trainer1) {
        this.trainer1 = trainer1;
    }

    public Trainer getTrainer2() {
        return trainer2;
    }

    public void setTrainer2(Trainer trainer2) {
        this.trainer2 = trainer2;
    }
}
