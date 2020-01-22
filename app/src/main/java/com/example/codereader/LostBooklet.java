package com.example.codereader;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LostBooklet extends AppCompatActivity {

    private static final String LOG_TAG = LostBooklet.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_booklet);
    }

    public void findPatient(View view){
        TextView textViewfirstName = (TextView) findViewById(R.id.id_firstname_lb);
        String firstName = textViewfirstName.getText().toString();

        TextView textViewLastName = (TextView) findViewById(R.id.id_lastname_lb);
        String lastName = textViewLastName.getText().toString();

        DatePicker datePicker = (DatePicker)findViewById(R.id.id_dob_lb);
        String date = ""+datePicker.getDayOfMonth();
        String month = ""+datePicker.getMonth();
        String year = ""+datePicker.getYear();
        String dateOfBirth = date+"-"+month+"-"+year;
    }

    private boolean containsPatient(String firstName, String lastName, String dob){
        //MainActivity.patientsFromDb.containsKey(input.trim())
        return false;
    }
}
