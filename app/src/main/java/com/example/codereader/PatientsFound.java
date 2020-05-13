package com.example.codereader;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codereader.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientsFound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_found);
        List<Patient> patients = new ArrayList<>();

        patients.add(new Patient("124", "dfg", "weer", "wer", "fsd", "f"));
        PatientListAdapter patientListAdapter = new PatientListAdapter(patients);
        RecyclerView recyclerView = findViewById(R.id.patient_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(patientListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String dob = intent.getStringExtra("dob");

    }
}
