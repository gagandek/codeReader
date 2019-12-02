package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChildProgram extends AppCompatActivity {


    private static final String LOG_TAG = ChildProgram.class.getSimpleName();
    private static final String STATE_COUNTER = "counter";
    private int mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_program);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String sex = intent.getStringExtra("sex");
        String uniqueID = intent.getStringExtra("uniqueID");

        TextView first = (TextView) findViewById(R.id.id_firstName_cp);
        first.setText(firstName);

        TextView second = (TextView) findViewById(R.id.id_lastname_cp);
        second.setText(lastName);

        TextView textViewSex = (TextView) findViewById(R.id.id_gender_cp);
        textViewSex.setText(sex);

        if(null!=uniqueID){
            TextView textViewID = (TextView) findViewById(R.id.id_uniqueid_cp);
            textViewID.setText(uniqueID);
        }


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_COUNTER, mCounter);
    }
}
