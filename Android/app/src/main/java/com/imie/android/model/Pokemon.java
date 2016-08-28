package com.imie.android.model;

import java.io.Serializable;

/**
 * Represent a Pokemon
 * Created by charly on 31/07/2016.
 */
public class Pokemon implements Serializable {

    Integer id;
    String name;
    Attack attack1;
    Attack attack2;
    Attack attack3;
    Attack attack4;
    PokemonType pokemon_type;
    PokemonFightState pokemon_fight_state;
    Integer level;
    Integer experience;
    Integer hp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attack getAttack1() {
        return attack1;
    }

    public void setAttack1(Attack attack1) {
        this.attack1 = attack1;
    }

    public Attack getAttack2() {
        return attack2;
    }

    public void setAttack2(Attack attack2) {
        this.attack2 = attack2;
    }

    public Attack getAttack3() {
        return attack3;
    }

    public void setAttack3(Attack attack3) {
        this.attack3 = attack3;
    }

    public Attack getAttack4() {
        return attack4;
    }

    public void setAttack4(Attack attack4) {
        this.attack4 = attack4;
    }

    public PokemonType getPokemonType() {
        return pokemon_type;
    }

    public void setPokemonType(PokemonType pokemonType) {
        this.pokemon_type = pokemonType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public PokemonFightState getPokemon_fight_state() {
        return pokemon_fight_state;
    }

    public void setPokemon_fight_state(PokemonFightState pokemon_fight_state) {
        this.pokemon_fight_state = pokemon_fight_state;
    }
}
