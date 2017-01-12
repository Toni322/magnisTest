package com.examplenasa.roma.magnistesttask;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Roma on 11.01.2017.
 */

public class DataFieds extends AsyncTask<String, Void, ArrayList<String>> {

    public static String LOG_TAG = "my_log";

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";


    @Override
    protected ArrayList<String> doInBackground(String... ticker) {

        try {

            URL url = new URL("https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?ticker="+ticker[0]+"&qopts.columns=Date,Open,High,Low,Close&api_key=He-mBpEmpDsBsUy3zszs");

            String u = url.toString();

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.d(LOG_TAG, u);

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject dataJsonObj = null;

        ArrayList<String> historyOHLC = new ArrayList<>();

        try {
            dataJsonObj = new JSONObject(resultJson);

            JSONObject datatable = dataJsonObj.getJSONObject("datatable");

            JSONArray data = datatable.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONArray jsonArray = data.getJSONArray(i);

                String date = jsonArray.getString(0);
                String open = jsonArray.getString(1);
                String high = jsonArray.getString(2);
                String low = jsonArray.getString(3);
                String close = jsonArray.getString(4);



                historyOHLC.add(i,"date: "+date+" open: "+open+" high: "+high+ " low: " + low + " close: " + close );

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return historyOHLC;

    }

}