package com.example.codereader;

import android.os.AsyncTask;
import android.util.Log;

public class WSCallTask extends AsyncTask<Void, String, Void> {

    private static final String LOG_TAG = WSCallTask.class.getSimpleName();

    @Override
    protected Void doInBackground(Void... voids) {

        publishProgress("Testing....");

        /** can call the methods from here? - to perform the different tasks */

        try{
            Log.d(LOG_TAG, "trying");

        } catch (Exception e){
            publishProgress("failed.\n");
        }
        return null;
    }
}
