package model;


import java.time.LocalDate;

public class Log {

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
}
