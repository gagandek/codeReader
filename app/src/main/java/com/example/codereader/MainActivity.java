package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
}
