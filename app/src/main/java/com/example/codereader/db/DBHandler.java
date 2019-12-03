package com.example.codereader.db;

import android.os.Environment;
import android.util.Log;

import com.example.codereader.DataHandlerActivity;
import com.example.codereader.model.Patient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Map;

import static com.example.codereader.MainActivity.patientsFromDb;

public class DBHandler{
    private static final String LOG_TAG = DBHandler.class.getSimpleName();

    final String INSTALLATION_DIR = "Android/data/dhis";
    final String dataFile = "data.txt";
    public static int idCounter = patientsFromDb.size();

    public String addPatient2(String[] params){
        boolean retValue = checkDuplicate(new Patient(null, params[0], params[1], params[2], params[3], params[4]));

        if(!retValue){
            String id = generateID();
            Patient patient = new Patient(id, params[0], params[1], params[2], params[3], params[4]);
            String tuple = "[" + patient.getUniqueID()
                    + ", " + patient.getFullname().trim()
                    + ", " + patient.getGender().trim() +
                    ", " + patient.getDob().trim() + "]\n";

            writeToFile(tuple, patient, retValue);
            patientsFromDb.put(id, patient);
            return id;
        }
        return null;
    }

    public boolean writeToFile(String tuple, Patient patient, boolean status){

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File parent = new File(externalStorageDirectory,INSTALLATION_DIR);
        File installation = new File(parent, dataFile);

        boolean val = false;
        try{
            if(!installation.exists()){
                if(!parent.exists()){
                    boolean mkdir = parent.mkdir();
                    Log.d(LOG_TAG, "mkdir: " + mkdir);
                }
                val = writeInstallationFile(installation, tuple, patient, status);
            }else{
                val = writeInstallationFile(installation, tuple, patient, status);
                Log.i(LOG_TAG, "writing to file: success");
            }
            return val;
        }catch (Exception e){
            Log.d(LOG_TAG, "Exception while writing to file");
            return val;
        }
    }

    private boolean writeInstallationFile(File installation, String tuple, Patient patient, boolean status) throws Exception {
        FileOutputStream out = new FileOutputStream(installation, true);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(out);

        if(!status){
            myOutWriter.append(tuple);
            Log.d(LOG_TAG, "Written to the file");
        }

        myOutWriter.close();
        out.flush();
        out.close();
        return status;
    }

    public static String generateID() {
        idCounter += 1;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        return decimalFormat.format(idCounter);
    }



    private static boolean checkDuplicate(Patient patient){
       for(Map.Entry<String, Patient> entry : patientsFromDb.entrySet()){
           String fullName = entry.getValue().getFullname();
           String gender = entry.getValue().getGender();
           String dob= entry.getValue().getDob();

           if(patient.getFullname().equals(fullName) && patient.getGender().equals(gender) && patient.getDob().equals(dob)){
               return true;
           }
       }
       return false;
    }

    public static void findPatient(String input) {
        //TODO: go tru map, find a match, send it (Patient) to the DataHandlerActivity w/data.
        if(patientsFromDb.containsKey(input)){
            Patient patient = patientsFromDb.get(input);
            DataHandlerActivity.redirect(patient);
        }
    }

}
