package com.imie.android.model;

import java.io.Serializable;

/**
 * Created by charly on 22/08/2016.
 */
public class PokemonFightState implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
