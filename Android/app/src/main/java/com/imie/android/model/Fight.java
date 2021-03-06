package com.imie.android.model;

import java.io.Serializable;

/**
 * Created by charly on 16/08/2016.
 */
public class Fight implements Serializable {

    private Integer id;
    private String date;
    private FightState fight_state;
    private Trainer trainer1;
    private Trainer trainer2;

    public Fight(Integer id, String date, FightState fight_state, Trainer trainer1, Trainer trainer2) {
        this.id = id;
        this.date = date;
        this.fight_state = fight_state;
        this.trainer1 = trainer1;
        this.trainer2 = trainer2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
