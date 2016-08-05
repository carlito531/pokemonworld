package com.imie.android.model;

/**
 * Represent a Pokemon
 * Created by charly on 31/07/2016.
 */
public class Pokemon {

    String name;
    String attack1;
    String attack2;
    String attack3;
    String attack4;
    String type;
    Integer level;
    Integer experience;
    Integer hp;

    public Pokemon(String name, String attack1, String attack2, String attack3, String attack4, String type, Integer level, Integer experience, Integer hp) {
        this.name = name;
        this.attack1 = attack1;
        this.attack2 = attack2;
        this.attack3 = attack3;
        this.attack4 = attack4;
        this.type = type;
        this.level = level;
        this.experience = experience;
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttack1() {
        return attack1;
    }

    public void setAttack1(String attack1) {
        this.attack1 = attack1;
    }

    public String getAttack2() {
        return attack2;
    }

    public void setAttack2(String attack2) {
        this.attack2 = attack2;
    }

    public String getAttack3() {
        return attack3;
    }

    public void setAttack3(String attack3) {
        this.attack3 = attack3;
    }

    public String getAttack4() {
        return attack4;
    }

    public void setAttack4(String attack4) {
        this.attack4 = attack4;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }
}
