package com.example.yuichiroutakahashi.kotlinrssreader.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDataHandler {

    static String stream = "";

    public HttpDataHandler() { }

    public String getHTTPDataHandler(String urlString) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                String line = "";

                while ((line = r.readLine()) != null ) {
                    stringBuilder.append(line);
                    stream = stringBuilder.toString();
                }

                connection.disconnect();
            }
        } catch (Exception exception) {
            return null;
        }

        return stream;
    }
}