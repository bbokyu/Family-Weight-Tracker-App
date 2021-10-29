package model;

import model.exceptions.NegativeValueException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;



public class Member implements Writable {

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

    //MODIFIES: this
    //EFFECTS: Adds given log to member's weight log
    public void addLogToWeightLog(Log log) {
        weightLog.add(log);
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


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("height", height);
        json.put("weightLog", weightLogToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray weightLogToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Log l : weightLog) {
            jsonArray.put(l.toJson());
        }

        return jsonArray;
    }


}

