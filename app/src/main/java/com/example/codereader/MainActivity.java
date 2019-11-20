package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_COUNTER = "counter";
    private int mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void readData(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivityQRStripped.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        new WSCallTask().execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_COUNTER, mCounter);
    }
}
