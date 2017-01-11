package com.examplenasa.roma.magnistesttask;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    public static String LOG_TAG = "my_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ParseTask().execute();
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL("https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?ticker=AMD&qopts.columns=Date,Open,High,Low,Close&api_key=He-mBpEmpDsBsUy3zszs");

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
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);


            JSONObject dataJsonObj = null;


            try {
                dataJsonObj = new JSONObject(strJson);

                JSONObject datatable = dataJsonObj.getJSONObject("datatable");

                JSONArray data = datatable.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                JSONArray jsonArray = data.getJSONArray(i);


                String date = jsonArray.getString(1);
                    Log.d(LOG_TAG, "One: " + date);
                }



                String tst = data.toString();




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}