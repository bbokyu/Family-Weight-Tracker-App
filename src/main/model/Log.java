package model;


import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

public class Log implements Writable {

    private Double weight;
    private String date;

    // A log represents a single entry, tracking their weight and the date
    public Log(Double weight) {
        this.weight = weight;
        LocalDate dateToday = LocalDate.now();
        String dateTodayInString = dateToday.toString();
        this.date = dateTodayInString;
    }

    public Double getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    // MODIFIES: this
    // EFFECTS: changes the date of the log to given date
    public void updateDate(String givenDate) {
        this.date = givenDate;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("weight", weight);
        json.put("date", date);
        return json;
    }
}
