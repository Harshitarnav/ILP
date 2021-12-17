package uk.ac.ed.inf;

import com.google.gson.Gson;

/**
 * Class to get the Coordinates of the location which is provided as a three word string.
 */
public class What3Words {
    String name;
    String port;

    public What3Words(String name, String port) {
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Class to parse the http url and get the coordinate location from the 3 word string location.
     * @param location a point on the map given in what3word format
     * @return coordinates of that location
     */
    public LongLat getCoordinates(String location) {
        String[] words = location.split("\\.");
        String url = "http://" + name + ":" + port + "/words/" + words[0] + "/" + words[1] + "/" + words[2] + "/" + "details.json";
        String jsonDetailsString = Client.doRequest(url);
        LngLatCoord longi_lati = new Gson().fromJson(jsonDetailsString, LngLatCoord.class);
        return longi_lati.coordinates;
    }
}

/**
 * Class to store all the coordinates received.
 */
class LngLatCoord {
    public LongLat coordinates;

}
