package com.example.codereader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivityQRStripped extends Activity {

    private static final String LOG_TAG = MainActivityQRStripped.class.getSimpleName();

    private final int CAMREA_CODE = 1;

    private CameraSource cameraSource;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_qr);

        if (!permissionGranted()) {
            // show alert not access to camera
            surfaceView = (SurfaceView) findViewById(R.id.camera_preview);
            surfaceView.setVisibility(View.INVISIBLE);
            requestPermission();
        } else {
            configureBarcodeDetector();
        }
    }

    private void configureBarcodeDetector() {
        surfaceView = (SurfaceView) findViewById(R.id.camera_preview);

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    // TODO:
                    e.printStackTrace();
                    Log.e(LOG_TAG, "Exception: " + e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if (qrCodes.size() > 0) {
                    String displayValue = qrCodes.valueAt(0).displayValue;
                    String[] split = displayValue.split("=");
                    if (split.length == 2) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                cameraSource.release();
                            }
                        });
                        Log.i(LOG_TAG, "displayValue: " + displayValue);
                        String value = split[1];
                        // TODO: do something with the vale from QR code
                        Log.d(LOG_TAG, "value: "+value);
                    }
                }
            }
        });
    }

    private boolean permissionGranted() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Log.v(LOG_TAG, "shouldShowRequestPermissionRationale");
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMREA_CODE);
        Log.v(LOG_TAG, "done");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean permissionGranted = false;
        if (requestCode == CAMREA_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
                Toast.makeText(this, "Permission granted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                if (!showRationale) {
                    openSettingsDialog();
                }
            }
            Log.v(LOG_TAG, "permissionGranted:" + permissionGranted);
            if (permissionGranted) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, SomeActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

    private void openSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityQRStripped.this);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}

