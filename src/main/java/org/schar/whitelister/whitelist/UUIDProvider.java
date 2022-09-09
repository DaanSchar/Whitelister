package org.schar.whitelister.whitelist;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class UUIDProvider {

    private static final String USER_API_URL = "https://api.mojang.com/users/profiles/minecraft/";

    public static Optional<JSONObject> getPlayerJSON(String username) {
        var wrappedConn = createGetConnection(USER_API_URL + username);

        if (wrappedConn.isPresent()) {
            HttpURLConnection conn = wrappedConn.get();

            if (ok(conn)) {
                try {
                    InputStream httpBody = conn.getInputStream();
                    String response = parseResponseToString(httpBody).orElseThrow();
                    return Optional.of(new JSONObject(response));
                } catch (IOException e) {
                    System.out.println("Something went wrong with the input stream.");
                    e.printStackTrace();
                }
            }
        }

        return Optional.empty();
    }

    private static Optional<HttpURLConnection> createGetConnection(String url) {
        try {
            URL urlObject;
            HttpURLConnection connection;

            urlObject = new URL(url);
            connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            return Optional.of(connection);
        } catch (MalformedURLException e) {
            System.out.println("Something went wrong with the url.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not establish connection with url.");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private static Optional<String> parseResponseToString(InputStream inputStream) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(responseContent.toString());
    }

    private static boolean ok(HttpURLConnection conn){
        try {
            return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            System.out.println("Failed to read http response");
            e.printStackTrace();
            return false;
        }
    }



}
