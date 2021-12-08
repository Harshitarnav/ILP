package uk.ac.ed.inf;

import com.mapbox.geojson.*;

import java.util.ArrayList;

public class Landmark {
    private final String port;
    private final String name;

    ArrayList<LongLat> landmarkPos = new ArrayList<>();

    public Landmark(String port, String name) {
        this.port = port;
        this.name = name;
        landmarkPos = new ArrayList<>();
    }

    private void getLandmarkPos() {
        String url = "http://" + name + ":" + port + "/building/" + "no-fly-zones.geojson";
        String jsonDetailsString = Client.doRequest(url);
        FeatureCollection featureCollection = FeatureCollection.fromJson(jsonDetailsString);
        ArrayList<Feature> featureArrayList = (ArrayList<Feature>) featureCollection.features();
        for(Feature f : featureArrayList) {
            Geometry g = f.geometry();
            Point p = (Point) g;
            landmarkPos.add(new LongLat(p.longitude(), p.latitude()));
        }
    }
}
