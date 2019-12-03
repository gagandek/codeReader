package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codereader.model.Patient;

public class DataHandlerActivity extends AppCompatActivity {

    private static final String LOG_TAG = DataHandlerActivity.class.getSimpleName();

    TextView textView;
    String rawData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_handler);

        Intent intent = getIntent();
        String rawData = intent.getStringExtra("rawData");
        setContentView(R.layout.activity_data_handler);

        if (rawData != null) {

            Log.d(LOG_TAG, "" + rawData);
            textView = (TextView) findViewById(R.id.info_text);
            textView.setText(rawData);
            this.rawData = rawData;
        } else {
            Log.d(LOG_TAG, " the rawData is null");
        }
    }

    public void scanANewPatient(View view) {
        finish();
        /** not the correct way to do it.. ? */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void reDirectToChildProgramme(View view) {

        Intent intent = getParams2(ChildProgram.class);
        startActivity(intent);
    }

    public void reDirectToART(View view) {

        Intent intent = getParams2(ART.class);
        startActivity(intent);
    }

    public void reDirectToTB(View view) {
        Intent intent = getParams2(TB.class);
        startActivity(intent);
    }

    private Intent getParams(Class classToCall) {

        String[] splited = rawData.split("\\r?\\n");
        String name = splited[0].substring(5).trim();
        String sex = splited[1].substring(4).trim();
        String dob = splited[2].substring(6).trim();

        String[] names = getNames(name);

        Log.d(LOG_TAG, "************************");
        Log.d(LOG_TAG, "Class: " + classToCall.getSimpleName());
        Log.d(LOG_TAG, "Full name: " + name);
        Log.d(LOG_TAG, "first name: " + names[0]);
        Log.d(LOG_TAG, "last name: " + names[1]);
        Log.d(LOG_TAG, "sex: " + sex);
        Log.d(LOG_TAG, "dob: " + dob);
        Log.d(LOG_TAG, "************************");

        Intent intent = new Intent(this, classToCall);
        intent.putExtra("name", name);
        intent.putExtra("firstName", names[0]);
        intent.putExtra("lastName", names[1]);
        intent.putExtra("sex", sex);
        intent.putExtra("dob", dob);

        return intent;
    }

    public Intent getParams2(Class classToCall){
        String[] splited = rawData.split("\\r?\\n");
        String name = null;
        String sex = null, dob = null, uniqueID = null;

        for(String s : splited){
            if(s.contains("Name")){
                name = s.substring(5).trim();
            }
            if(s.contains("Sex")){
                sex = s.substring(4).trim();
            }
            if(s.contains("D.o.b")){
                dob = s.substring(6).trim();
            }
            if(s.contains("UniqueID")){
                uniqueID = s.substring(9).trim();
            }
        }

        String[] names = getNames(name);

        Log.d(LOG_TAG, "************************");
        Log.d(LOG_TAG, "Class: " + classToCall.getSimpleName());
        Log.d(LOG_TAG, "Full name: " + name);
        Log.d(LOG_TAG, "first name: " + names[0]);
        Log.d(LOG_TAG, "last name: " + names[1]);
        Log.d(LOG_TAG, "sex: " + sex);
        Log.d(LOG_TAG, "dob: " + dob);
        Log.d(LOG_TAG, "uniqueID: " + uniqueID);
        Log.d(LOG_TAG, "************************");

        Intent intent = new Intent(this, classToCall);
        intent.putExtra("name", name);
        intent.putExtra("firstName", names[0]);
        intent.putExtra("lastName", names[1]);
        intent.putExtra("sex", sex);
        intent.putExtra("dob", dob);
        intent.putExtra("uniqueID", uniqueID);

        return intent;
    }

    public static String[] getNames(String fullName){

        String[] names = fullName.split(" ");
        if (2 == names.length) {
            //only first and last name
            return names;

        } else if (names.length > 2) {
            //more than one names - first, middle, last etc

            String wholeFirstName = null;
            for (int i = 0; i < names.length; i++) {
                wholeFirstName += names[i];
                wholeFirstName += " ";
            }
            String[] moreNames = new String[2];
            moreNames[0] = wholeFirstName;

            moreNames[1] = names[names.length - 1];
            return moreNames;

        } else if(names.length == 1){
            //one name
            return new String[] {fullName};
        }

        return null;
    }

    public static void redirect(Patient patient){

    }
}

