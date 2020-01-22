package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewBooklet extends AppCompatActivity {

    private static final String LOG_TAG = NewBooklet.class.getSimpleName();
    String id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booklet);

        String info = "Print a new code for the patient by searching with the patient ID (unique ID)";
        TextView textViewInfo = (TextView) findViewById(R.id.id_info_nb);
        textViewInfo.setText(info);
    }

    public void printCode(View view) {
        TextView textViewId = (TextView) findViewById(R.id.id_input_nb);
        id = textViewId.getText().toString();
        Log.d(LOG_TAG, "ID: " + id);

        Intent intent = new Intent(this, PrintQRCode.class);
        intent.putExtra("id", id);
        intent.putExtra("path", MainActivity.QR_PATH);
        startActivity(intent);
    }
}
