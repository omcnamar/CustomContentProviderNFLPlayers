package com.olegsagenadatrytwo.customcontentprovidernflplayers;

/**
 * Created by omcna on 8/23/2017.
 */

public class NFLPlayer {
    String name;
    String age;
    String team;
    String position;
    String ratings;

    public NFLPlayer() {
    }

    public NFLPlayer(String name, String age, String team, String position, String ratings) {

        this.name = name;
        this.age = age;
        this.team = team;
        this.position = position;
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
