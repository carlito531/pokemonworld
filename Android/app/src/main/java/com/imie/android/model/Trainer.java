package com.imie.android.model;

import java.util.List;

/**
 * Created by charly on 06/08/2016.
 */
public class Trainer {

    private String name;
    private String login;
    private boolean is_master;
    private List<Badge> badges;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean is_master() {
        return is_master;
    }

    public void setIs_master(boolean is_master) {
        this.is_master = is_master;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
}
