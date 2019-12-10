package com.example.codereader;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrintQRCode extends AppCompatActivity {

    private static final String LOG_TAG = PrintQRCode.class.getSimpleName();
    String path, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_qrcode);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        id = intent.getStringExtra("id");

        TextView idTextView = (TextView) findViewById(R.id.id_uniqueid_pqr);
        idTextView.setText("Unique ID: " + id);

        addImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    public void printTheCode(View view) {
    }

    private void addImage() {
        try {
            ImageView imageView = (ImageView) findViewById(R.id.imageView_pqr);
            imageView.setImageBitmap(BitmapFactory.decodeFile(path + id + ".png"));
            Log.d(LOG_TAG, "Image added");
        } catch (Exception e) {
            Log.d(LOG_TAG, "could not view the image ");
        }
    }
}
