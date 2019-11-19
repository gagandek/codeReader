package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String sex = intent.getStringExtra("sex");
        String dob = intent.getStringExtra("dob");

        String[] names = name.split(" ");
        if (2 == names.length) {
            //only first and last name

            TextView first = (TextView) findViewById(R.id.editText_firstname);
            first.setText(names[0]);
            TextView second = (TextView) findViewById(R.id.editText_lastname);
            second.setText(names[1]);

        } else if (names.length > 2) {
            //more than one names - first, middle, last etc

            String wholeFirstName = null;
            for (int i = 0; i < names.length; i++) {
                wholeFirstName += names[i];
                wholeFirstName += " ";
            }
            TextView first = (TextView) findViewById(R.id.editText_firstname);
            first.setText(wholeFirstName);

            TextView second = (TextView) findViewById(R.id.editText_lastname);
            second.setText(names[names.length - 1]);

        } else {
            //one name
        }

        TextView textViewSex = (TextView) findViewById(R.id.editText_sex);
        textViewSex.setText(sex);

        TextView textViewDob = (TextView) findViewById(R.id.editText_dob);
        textViewDob.setText(dob);

    }

    public void searchInDB(View view) {
    }
}
