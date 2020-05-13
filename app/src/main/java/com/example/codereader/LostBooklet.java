package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codereader.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.codereader.MainActivity.patientsFromDb;

public class LostBooklet extends AppCompatActivity {

    private static final String LOG_TAG = LostBooklet.class.getSimpleName();
    List<Patient> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_booklet);

        matches = new ArrayList<>();
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

        boolean found = containsPatient(firstName, lastName, dateOfBirth);
        if(!found){
            Toast.makeText(getApplicationContext(), "No patients found! ", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, PatientsFound.class);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName", lastName);
            intent.putExtra("dob", dateOfBirth);
            startActivity(intent);
        }
    }

    private boolean containsPatient(String firstName, String lastName, String dob){

        for (Map.Entry<String, Patient> entry : patientsFromDb.entrySet()) {
            String fn = entry.getValue().getFirstName();
            String ln = entry.getValue().getLastName();
            String dobb = entry.getValue().getDob();

            if(firstName.equals(fn) && lastName.equals(ln) && dob.equals(dobb)){
                matches.add(entry.getValue());
            }
        }

        if(matches != null){
            return true;
        }
        return false;
    }
}
