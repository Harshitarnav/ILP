package uk.ac.ed.inf;

import com.mapbox.geojson.*;

import java.util.ArrayList;


/**
 * Class to store the location of the landmarks.
 */
public class Landmark {
    private final String port;
    private final String name;
    ArrayList<LongLat> landmarkPos;

    public Landmark(String name, String port) {
        this.port = port;
        this.name = name;
        landmarkPos = new ArrayList<>();
        getLandmarkPos();
    }

    /**
     * Function to parse the url and then store the longitude-latitude position for later use.
     */
    private void getLandmarkPos() {
        String url = "http://" + name + ":" + port + "/buildings/" + "landmarks.geojson";
        String jsonDetailsString = Client.doRequest(url);
        FeatureCollection featureCollection = FeatureCollection.fromJson(jsonDetailsString);
        ArrayList<Feature> featureArrayList = (ArrayList<Feature>) featureCollection.features();
        assert featureArrayList != null;
        for(Feature f : featureArrayList) {
            Geometry g = f.geometry();
            Point p = (Point) g;
            assert p != null;
            landmarkPos.add(new LongLat(p.longitude(), p.latitude()));
        }
    }

    /**
     * Getter to get a list of landmark positions.
     * @return ArrayList of Longitude and Latitude containing parsed points
     */
    public ArrayList<LongLat> getLandmarks() {
        return landmarkPos;
    }
}
