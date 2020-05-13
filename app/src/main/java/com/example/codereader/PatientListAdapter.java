package com.example.codereader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codereader.model.Patient;

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.MyViewHolder> {
    List<Patient> patientList;
    public PatientListAdapter(List<Patient> patientList){
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.textView.setText(patient.toString());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id_textView_il);
        }
    }
}
