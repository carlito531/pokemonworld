package com.imie.android.model;

import java.io.Serializable;

/**
 * Created by charly on 16/08/2016.
 */
public class FightState implements Serializable{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
