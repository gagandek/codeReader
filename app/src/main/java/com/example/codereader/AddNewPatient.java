package com.example.codereader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.codereader.db.DBHandler;

import java.util.Calendar;

public class AddNewPatient extends AppCompatActivity {

    private static final String LOG_TAG = AddNewPatient.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    DBHandler dbHandler;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DBHandler();
        setContentView(R.layout.activity_add_new_patient);
        checkWritePermissions();
        checkReadPermissions();
    }

    public void addPatientToDB(View view) {

        TextView textViewFirstName = (TextView) findViewById(R.id.id_firstname_anp);
        String firstName = textViewFirstName.getText().toString();

        TextView textViewLastName = (TextView) findViewById(R.id.id_lastname_anp);
        String lastName = textViewLastName.getText().toString();

        String dob = addDateOfBirth(view);
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
                String newId = dbHandler.addPatient2(new String[]{firstName + " " + lastName, firstName, lastName, dob, gender});
                if (null != newId) {
                    Toast.makeText(getApplicationContext(), "Patient added with id: " + newId, Toast.LENGTH_LONG).show();
                    Thread.sleep(500);
                    textViewFirstName.setText("");
                    textViewLastName.setText("");
                    Log.d(LOG_TAG, "Patient added: \n" + newId);
                    startPrintActivity(QRCodeGenerator.getPath(), newId);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Patient already exists", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(LOG_TAG, "could not add the patient");
            }
        }
    }

    private void startPrintActivity(String path, String id) {
        Intent intent = new Intent(this, PrintQRCode.class);
        intent.putExtra("path", path);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private boolean validate(String firstName, String lastName, String dob) {
        if (!firstName.isEmpty() && !lastName.isEmpty() && !dob.isEmpty()) {
            return true;
        }
        Toast.makeText(getApplicationContext(), "Missing input", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean checkWritePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not  granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Log.d(LOG_TAG, "first");
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }

        }else{
            // Permission has already been granted
            Log.d(LOG_TAG, "Permission already granted");
        }
        return true;
    }

    public boolean checkReadPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not  granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Log.d(LOG_TAG, "first");
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }

        }else{
            // Permission has already been granted
            Log.d(LOG_TAG, "Permission already granted");
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public String addDateOfBirth(View view) {
        DatePicker datePicker = (DatePicker)findViewById(R.id.id_datepicker_anp);
        String date = ""+datePicker.getDayOfMonth();
        String month = ""+datePicker.getMonth();
        String year = ""+datePicker.getYear();
        String dateOfBirth = date+"-"+month+"-"+year;

        return dateOfBirth;
    }

}
