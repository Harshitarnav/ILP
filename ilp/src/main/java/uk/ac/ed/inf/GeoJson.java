package uk.ac.ed.inf;

import com.mapbox.geojson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Class to read and write the output in GeoJSON format.
 */
public class GeoJson {

    ArrayList<Point> dronePath = new ArrayList<>();

    /**
     * Instead of using constructors, we use this function to convert each coordinate from longitude-latitude format to Point format
     * using Mapbox SDK's static method Point.fromLngLat.
     * @param nextLocation parameter representing the coordinates of point in longitude-latitude.
     */
    public void addLocation(LongLat nextLocation) {
        dronePath.add(Point.fromLngLat(nextLocation.lng, nextLocation.lat));
    }

    /**
     * function to write the file(save the file's data) on the GeoJSON file.
     * @param date The date we are working on
     */
    public void writeFile(String date) {
        LineString full_dronePath = LineString.fromLngLats(dronePath); //Getting an ArrayList of all the positions
        Feature feature = Feature.fromGeometry(full_dronePath); //Casting the points to Geometry
        FeatureCollection featureCollection = FeatureCollection.fromFeature(feature);
        String jsonDetailsString = featureCollection.toJson();

        try { //Creating a file
            File fileObj = new File("drone-" + date + ".geoJson");
            if (fileObj.createNewFile()) {
                System.out.println("File created: " + fileObj.getName());
            }
        }
        catch (IOException e) {
            System.err.println("Exception occurred : " + e.getMessage());
            e.printStackTrace();
        }

        try { // Populating the file
            FileWriter writer = new FileWriter("drone-" + date + ".geoJson");
            writer.write(jsonDetailsString);
            writer.close();
        }
        catch (IOException e) {
            System.err.println("Exception occurred : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
