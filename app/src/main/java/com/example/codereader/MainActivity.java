package com.example.codereader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.codereader.model.Patient;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    public static Map<String, Patient> patientsFromDb;
    final static String INSTALLATION_DIR = "Android/data/dhis";
    final static String QR_PATH = Environment.getExternalStorageDirectory().getPath()+"/QRCode/";
    final static String dataFile = "data.txt";
    public static int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        patientsFromDb = new HashMap<>();
        checkReadPermissions();
        readFromDb();   //fetching data
        counter = patientsFromDb.size();
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
        startActivity(intent);
    }

    public static void readFromDb() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File parent = new File(externalStorageDirectory, INSTALLATION_DIR);
        File installation = new File(parent, dataFile);
        try {
            if (!installation.exists()) {
                if (!parent.exists()) {
                    boolean mkdir = parent.mkdir();
                    Log.d(LOG_TAG, "mkdir: " + mkdir);
                    Log.d(LOG_TAG, "could not find the file");
                }
            } else {
                Log.d(LOG_TAG, "----> reading the file");
                String rawData = readInstallationFile(installation);
                Log.d(LOG_TAG, "************");
                Log.d(LOG_TAG, rawData);
                Log.d(LOG_TAG, "************");
                addToMap(rawData);
            }
            Log.d(LOG_TAG, "read from file: success");
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception while reading from file");
        }
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void addToMap(String rawData) {
        int counter = 0;
        String[] tuples = rawData.split("\\r?\\n");
        String name, sex, dob, uniqueID, village, physicalAdress;

        for (String t : tuples) {
            counter++;
            String[] tupleArray = t.split(",");
            Log.d(LOG_TAG, "***** SIZE: " + tupleArray.length);
            uniqueID = tupleArray[0].substring(1).trim();
            name = tupleArray[1].trim();
            sex = tupleArray[2].trim();
            dob = tupleArray[3].substring(0, tupleArray[3].length() - 1).trim();
            String[] names = DataHandlerActivity.getNames(name);
            Patient p;
            if(tupleArray.length>4){

                dob = tupleArray[3].trim();
                village = tupleArray[4].trim();
                physicalAdress = tupleArray[5].trim();
                Log.d(LOG_TAG , "NOW: " + dob + " " + village +" "+physicalAdress );
                p = new Patient(uniqueID, name, names[0], names[1], dob, sex, village, physicalAdress);
            }else{

                p = new Patient(uniqueID, name, names[0], names[1], dob, sex);
            }
            patientsFromDb.put(uniqueID, p);
            Log.d(LOG_TAG, p.toString());
        }
        Log.d(LOG_TAG, "counter: " + counter);
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

        } else {
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

    public void lostBooklet(View view) {
    }

    public void newBooklet(View view) {
        Intent intent = new Intent(this, NewBooklet.class);
        startActivity(intent);
    }
}
