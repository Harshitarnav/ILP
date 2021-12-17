package uk.ac.ed.inf;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Web Client used for loading and parsing the relevant data from the HTTP and jdbc servers.
 *
 * @author Arnav Sinha
 */

public class Client {

    //General method for loading data string from the server.
    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Client method to parse JSON into Java Object.
     */
    public static String doRequest(String url) {
        String jsonDetailsString = "";
        //HTTP GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response;

        {
            //Sending request to the client.
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
            catch (IOException | InterruptedException e) {
                System.err.println("Exception occurred : " + e.getMessage());
            e.printStackTrace();
        }
        }
        return jsonDetailsString;
    }
}
