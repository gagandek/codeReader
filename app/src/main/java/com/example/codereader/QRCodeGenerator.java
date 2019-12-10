package com.example.codereader;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.codereader.model.Patient;
import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRCodeGenerator {

    private static final String LOG_TAG = QRCodeGenerator.class.getSimpleName();
    String inputValue;
    String savePath = Environment.getExternalStorageDirectory().getPath()+"/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    public static String path;

    public QRCodeGenerator(Patient patient){
        this.inputValue = dataToStore(patient);
        int smallerDimension = 18*18;
        path = savePath;
        qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);

        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            Log.d(LOG_TAG, "encoded");
        }catch (WriterException e){
            Log.d(LOG_TAG, "ooops");
        }
    }

    public boolean saveCode(String tag){
        try {
            return QRGSaver.save(savePath, tag, bitmap, QRGContents.ImageType.IMAGE_PNG);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String dataToStore(Patient patient){
        String tuple = "Name: "+patient.getFullname() + "\n" +
                "Sex: " + patient.getGender() + "\n"+
                "D.o.b: " + patient.getDob() + "\n"+
                "UniqueID: " + patient.getUniqueID()+ "\n";
        return tuple;
    }

    public static String getPath(){
        return path;
    }
}
