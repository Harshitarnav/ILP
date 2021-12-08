package uk.ac.ed.inf;

import com.mapbox.geojson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GeoJson {

    ArrayList<Point> dronePath = new ArrayList<>();

    public void addLocation(LongLat nextLocation) {
        dronePath.add(nextLocation.loctoPoint());
    }

    public void writeFile(String date) {
        LineString full_dronePath = LineString.fromLngLats(dronePath);
        Feature feature = Feature.fromGeometry(full_dronePath);
        FeatureCollection featureCollection = FeatureCollection.fromFeature(feature);
        String jsonDetailsString = featureCollection.toJson();

        try {
            File fileObj = new File("drone-" + date + ".geoJson");
            if (fileObj.createNewFile()) {
                System.out.println("File created: " + fileObj.getName());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("drone-" + date + ".geoJson");
            writer.write(jsonDetailsString);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
