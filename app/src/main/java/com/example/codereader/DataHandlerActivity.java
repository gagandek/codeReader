package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DataHandlerActivity extends AppCompatActivity {

    private static final String LOG_TAG = DataHandlerActivity.class.getSimpleName();

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_handler);

        Intent intent = getIntent();
        String rawData = intent.getStringExtra("rawData");
        setContentView(R.layout.activity_data_handler);

        if(rawData!= null){

            Log.d(LOG_TAG, ""+rawData);
            textView = (TextView) findViewById(R.id.info_text);
            textView.setText(rawData);
        }
     }
}
