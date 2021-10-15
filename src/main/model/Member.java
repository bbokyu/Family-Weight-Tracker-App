package model;

import java.util.ArrayList;



public class Member {

    private String name;
    private int height;
    private ArrayList<Log> weightLog;

    // Represents a member of family with name and weight
    public Member(String name, int height) {
        this.name = name;
        this.height = height;
        weightLog = new ArrayList<>();
    }


    public void addWeightLog(Double weight) {
        Log temp = new Log(weight);
        weightLog.add(temp);
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }





}

