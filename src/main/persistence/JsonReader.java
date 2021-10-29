package persistence;


//modeled from  JsonSerializationDemo Project
// Represents a reader that reads workroom from JSON data stored in file

import model.Log;
import model.Member;
import model.exceptions.NegativeValueException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads member from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Member read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMember(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses member from JSON object and returns it
    private Member parseMember(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int height = jsonObject.getInt("height");
        Member m = new Member(name, height);
        addWeightLog(m, jsonObject);
        return m;
    }

    // MODIFIES: m
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addWeightLog(Member m, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("weightLog");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            try {
                addLog(m, nextLog);
            } catch (NegativeValueException e) {
                System.out.println("Recalled Negative weight from file");
            }
        }
    }

    // MODIFIES: m
    // EFFECTS: parses Log from JSON object and adds it to Member
    private void addLog(Member m, JSONObject jsonObject) throws NegativeValueException {
        Double weight = jsonObject.getDouble("weight");
        LocalDate date = (LocalDate) jsonObject.get("date");
        Log log = new Log(weight);
        log.updateDate(date);
        m.addLogToWeightLog(log);
    }




}
