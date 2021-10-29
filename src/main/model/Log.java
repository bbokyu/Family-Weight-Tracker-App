package model;


import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

public class Log implements Writable {

    private Double weight;
    private LocalDate date;

    // A log represents a single entry, tracking their weight and the date
    public Log(Double weight) {
        this.weight = weight;
        date = LocalDate.now();
    }

    public Double getWeight() {
        return weight;
    }

    public LocalDate getDate() {
        return date;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("weight", weight);
        json.put("date", date);
        return json;
    }
}
