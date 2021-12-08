package uk.ac.ed.inf;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

/**
 * Web Client used for loading and parsing the relevant data from the HTTP server.
 *
 * @author Arnav Sinha
 */

public class Client {

    public ArrayList<MenuDetails> details;

    /**
     * General method for loading data string from the server.
     */

    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Client method to parse JSON into Java Object.
     */
    public static String doRequest(String url) {
        String jsonDetailsString = "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response;

        {
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                //parse JSON into object

                if (response.statusCode() == 200) {
                    //Success we have the data.
                    jsonDetailsString = response.body();
                } else {
                    //Failure
                    System.exit(1);
                }
            }
            catch (IOException e) {
            e.printStackTrace();
        }   catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return jsonDetailsString;
    }
}
