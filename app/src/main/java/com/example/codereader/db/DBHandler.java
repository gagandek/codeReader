package com.example.codereader.db;

import android.os.Environment;
import android.util.Log;
import com.example.codereader.model.Patient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class DBHandler{
    private static final String LOG_TAG = DBHandler.class.getSimpleName();

    public static List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient patient){
        Log.d(LOG_TAG, "****** kommer inn i add patient");
        String tuple = "[ " + patient.getUniqueID()
                + ", " + patient.getFullname()
                + ", " + patient.getGender() +
                ", " + patient.getDob() + "]\n";

        if(contains(patient)){
            patients.add(patient);
            Log.d(LOG_TAG, "Patient added to the list");
        }else{
            Log.d(LOG_TAG, "Patient already exists");
        }
    }

    public boolean contains(Patient patient){
        for(Patient p : patients){
            if(p.getUniqueID() == patient.getUniqueID()){
                return false;
            }
        }
        return true;
    }

    private void writeToFile(String tuple) {
        final File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/Datafiles");

        if(!path.exists()){
            path.mkdirs();
        }
        final File file = new File(path, "db.txt");
        try{
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fout);
            myOutWriter.append(tuple);
            myOutWriter.close();
            fout.flush();
            fout.close();
        }catch (IOException e){
            e.printStackTrace();
            Log.d(LOG_TAG, "could not add to the file");
        }
    }
    public void getPatient(int id) {

    }
}
