package model;

import model.exceptions.NegativeValueException;

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


    //MODIFIES: this
    //EFFECTS: Adds given weight to member's weight log
    public void addWeightLog(Double weight) throws NegativeValueException {
        if (weight < 0) {
            throw new NegativeValueException();
        }
        Log temp = new Log(weight);
        weightLog.add(temp);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Log> getWeightLogs() {
        return weightLog;
    }

    public int getHeight() {
        return height;
    }





}

