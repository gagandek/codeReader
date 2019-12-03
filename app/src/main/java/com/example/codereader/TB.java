package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TB extends AppCompatActivity {

    private static final String LOG_TAG = TB.class.getSimpleName();
    private static final String STATE_COUNTER = "counter";
    private int mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String sex = intent.getStringExtra("sex");
        String dob = intent.getStringExtra("dob");
        String uniqueID = intent.getStringExtra("uniqueID");

        TextView first = (TextView) findViewById(R.id.editText_firstname);
        first.setText(firstName);

        TextView second = (TextView) findViewById(R.id.editText_lastname);
        second.setText(lastName);

        TextView textViewSex = (TextView) findViewById(R.id.editText_sex);
        textViewSex.setText(sex);

        TextView textViewDob = (TextView) findViewById(R.id.editText_dob);
        textViewDob.setText(dob);

        if(null != uniqueID){
            TextView textViewID = (TextView) findViewById(R.id.editText_unique_id);
            textViewID.setText(uniqueID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_COUNTER, mCounter);
    }

    public void searchInDB(View view) {
        //TODO: search in the list of all the patients. DBHandler class has the list.
    }
}
