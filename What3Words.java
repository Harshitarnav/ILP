package uk.ac.ed.inf;

import com.google.gson.Gson;

public class What3Words {
    private final String port;
    private final String name;

    public What3Words(String port, String machineName) {
        this.port = port;
        this.name = machineName;
    }

    public LongLat getCoordinates(String location) {
        String[] words = location.split("\\.");
        String url = "http/:" + name + ":" + "/words/" + words[0] + "/" + words[1] + "/" + words[2] + "/" + "details.json";
        String jsonDetailsString = Client.doRequest(url);
        Coordinates longi_lati = new Gson().fromJson(jsonDetailsString, Coordinates.class);
        return longi_lati.coordinates;
    }
}

class Coordinates {
    public LongLat coordinates;
}
