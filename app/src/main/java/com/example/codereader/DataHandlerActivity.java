package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DataHandlerActivity extends AppCompatActivity {

    private static final String LOG_TAG = DataHandlerActivity.class.getSimpleName();

    TextView textView;
    String rawData;

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
            this.rawData = rawData;
        }else{
            Log.d(LOG_TAG, " the rawData is null");
        }
     }

    public void scanANewPatient(View view) {
        finish();
        /** not the correct way to do it */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void reDirectToMalaria(View view) {
    }

    public void reDirectToART(View view) {
    }

    public void reDirectToTB(View view) {
        String[] splited = rawData.split("\\r?\\n");
        String name = splited[0].substring(5).trim();
        String sex = splited[1].substring(4).trim();
        String dob = splited[2].substring(6).trim();

        Log.d(LOG_TAG, "******************");
        Log.d(LOG_TAG, "name: " + name);
        Log.d(LOG_TAG, "sex: " + sex);
        Log.d(LOG_TAG, "dob: " + dob);
        Log.d(LOG_TAG, "******************");

        Intent intent = new Intent(this, TB.class);
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("dob", dob);
        startActivity(intent);
    }
}
