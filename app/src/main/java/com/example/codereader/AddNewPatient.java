package com.example.codereader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codereader.db.DBHandler;

public class AddNewPatient extends AppCompatActivity {

    DBHandler dbHandler;
    private static final String LOG_TAG = AddNewPatient.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DBHandler();
        setContentView(R.layout.activity_add_new_patient);
    }

    public void addPatientToDB(View view) {

        TextView textViewFirstName = (TextView) findViewById(R.id.id_firstname_anp);
        String firstName = textViewFirstName.getText().toString();

        TextView textViewLastName = (TextView) findViewById(R.id.id_lastname_anp);
        String lastName = textViewLastName.getText().toString();

        TextView textViewDob = (TextView) findViewById(R.id.id_date_anp);
        String dob = textViewDob.getText().toString();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiobutton_group);
        int selectedGender = radioGroup.getCheckedRadioButtonId();
        Button btnDisplay = (RadioButton) findViewById(selectedGender);

        if (!validate(firstName, lastName, dob)) {
            Toast.makeText(getApplicationContext(), "Missing input...", Toast.LENGTH_SHORT).show();
        } else {

            if (btnDisplay == null) {
                Toast.makeText(getApplicationContext(), "Missing input", Toast.LENGTH_SHORT).show();
                return;
            }
            String gender = (String) btnDisplay.getText();
            try {
                String newId = dbHandler.addPatient2(new String[]{firstName+ " " +lastName, firstName, lastName, dob, gender});
                if(null != newId){
                    Toast.makeText(getApplicationContext(), "Patient added with id: " + newId, Toast.LENGTH_LONG).show();
                    Thread.sleep(500);
                    textViewFirstName.setText("");
                    textViewLastName.setText("");
                    textViewDob.setText("");
                    Log.d(LOG_TAG, "Patient added: \n" + newId);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Patient already exists", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Log.d(LOG_TAG, "could not add the patient");
            }
        }
    }

    private boolean validate(String firstName, String lastName, String dob) {
        if (!firstName.isEmpty() && !lastName.isEmpty() && !dob.isEmpty()) {
            return true;
        }
        Toast.makeText(getApplicationContext(), "Missing input", Toast.LENGTH_SHORT).show();
        return false;
    }
}
