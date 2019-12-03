package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codereader.model.Patient;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static Map<String, Patient> patientsFromDb;
    final static String INSTALLATION_DIR = "Android/data/dhis";
    final static String dataFile = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        patientsFromDb = new HashMap<>();
        readFromDb();   //fetching data
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
                    return;
                }
            }else{
                String rawData = readInstallationFile(installation);
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

        String[] tuples = rawData.split("\\r?\\n");
        String name, sex, dob, uniqueID;

        for (String t : tuples) {
            String[] tupleArray = t.split(",");
            uniqueID = tupleArray[0].substring(1).trim();
            name = tupleArray[1].trim();
            sex = tupleArray[2].trim();
            dob = tupleArray[3].substring(0, tupleArray[3].length()-1).trim();

            String[] names = DataHandlerActivity.getNames(name);
            Patient p = new Patient(uniqueID, name, names[0], names[1], dob, sex);
            patientsFromDb.put(uniqueID, p);
            Log.d(LOG_TAG, p.toString());
        }
    }
}
