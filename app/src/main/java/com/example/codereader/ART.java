package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ART extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String sex = intent.getStringExtra("sex");
        String dob = intent.getStringExtra("dob");
        String uniqueID = intent.getStringExtra("uniqueID");

        TextView first = (TextView) findViewById(R.id.id_firstname_anp);
        first.setText(firstName);

        TextView second = (TextView) findViewById(R.id.id_lastname_anp);
        second.setText(lastName);

        TextView textViewSex = (TextView) findViewById(R.id.id_gender_anp);
        textViewSex.setText(sex);

        TextView textViewdob = (TextView) findViewById(R.id.id_dob_anp);
        textViewdob.setText(dob);

        if(null!=uniqueID){
            TextView textViewID = (TextView) findViewById(R.id.id_uniqueid_anp);
            textViewID.setText(uniqueID);
        }
    }
}
