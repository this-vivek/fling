package com.example.fling.Helper;

import android.util.Log;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Connection
{
    public static HttpsURLConnection createConnection(String filename) {
        HttpsURLConnection connection = null;
        String path = "https://flingawing.000webhostapp.com/fling/" + filename;
        try {
            URL url = new URL(path);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            Log.d("Connection", "Connection established");
        } catch (Exception e) {
            Log.d("Connection", e.toString());
        }
        return connection;
    }
}
