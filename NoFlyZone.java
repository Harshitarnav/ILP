package uk.ac.ed.inf;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.*;

import java.util.ArrayList;

public class NoFlyZone {
    ArrayList<Polygon> polygonsOfNoFlyZone;
    String port;
    String name;

    public NoFlyZone(String port, String name) {
        polygonsOfNoFlyZone = new ArrayList<>();
        this.port = port;
        this.name = name;
    }

    private void getNoFlyZone() {
        String url = "http://" + name + ":" + port + "/building/" + "no-fly-zones.geojson";
        String jsonDetailsString = Client.doRequest(url);
        FeatureCollection featureCollection = FeatureCollection.fromJson(jsonDetailsString);
        ArrayList<Feature> featureArrayList = (ArrayList<Feature>) featureCollection.features();
        for(Feature f : featureArrayList) {
            Geometry g = f.geometry();
            Polygon p = (Polygon) g;
            polygonsOfNoFlyZone.add(p);
        }
    }
}
