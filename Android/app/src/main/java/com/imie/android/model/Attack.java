package com.imie.android.model;

import java.io.Serializable;

/**
 * Created by charly on 05/08/2016.
 */
public class Attack implements Serializable {

    private String name;
    private Integer damage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }
}
