package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_COUNTER = "counter";
    private int idCounter = 111111;

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void addToDb(View view) {
        Intent intent = new Intent(this, AddNewPatient.class);
        intent.putExtra("id", generateID());
        startActivity(intent);
    }

    public int generateID(){
        idCounter +=1;
        return idCounter;
    }
}
