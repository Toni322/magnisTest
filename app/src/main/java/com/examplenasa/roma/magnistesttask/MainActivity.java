package com.examplenasa.roma.magnistesttask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {



    public static String LOG_TAG = "my_log";
    ArrayList<String> listOHLC;

    private AutoCompleteTextView textViewTicker;

    private ListView listViewOHLC;
    private  Spinner spinner;
    private Button buttonOk;

    DataFieds dataFieds;
    ArrayAdapter<String> listAdapter;


    String[] example = new String[]{
            "AAPL", "ABM", "ACE", "AMD"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSpinner();


        ArrayAdapter<String> adapterTextViewTicker = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, example);
        textViewTicker = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewTicker);
        textViewTicker.setAdapter(adapterTextViewTicker);

        buttonOk = (Button) findViewById(R.id.okButton);
        View.OnClickListener listenerTickers = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         readResponce();
            }
        };
        buttonOk.setOnClickListener(listenerTickers);

        dataFieds  = new DataFieds();




    }

    private void addSpinner(){
        String[] dataFeeds = {"Quandl.com", "not available in this version :("};
        ArrayAdapter<String> adapterSpiner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataFeeds);
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapterSpiner);

         spinner.setPrompt("Select datafeed");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position == 1) {
                    Toast.makeText(getBaseContext(), "Sorry :(", Toast.LENGTH_SHORT).show();
                    spinner.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    private void readResponce(){
        String a = textViewTicker.getText().toString();

        dataFieds  = new DataFieds();

        listViewOHLC =  (ListView) findViewById(R.id.listViewOHLC);

        dataFieds.execute(a);

        try {
            listOHLC = dataFieds.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(listOHLC.isEmpty()){
          Toast.makeText(getApplicationContext(), "Wrong name!", Toast.LENGTH_SHORT).show();
        }

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOHLC);
        listViewOHLC.setAdapter(listAdapter);

         Log.d(LOG_TAG,"test "+ listOHLC);
    }


}