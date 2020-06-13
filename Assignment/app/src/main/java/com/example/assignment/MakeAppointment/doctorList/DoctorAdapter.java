package com.example.assignment.MakeAppointment.doctorList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.AppointmentsViews.AppointmentAdapter;
import com.example.assignment.Model.Doctor;
import com.example.assignment.R;
import com.example.assignment.localDb.AppointmentDB;

import java.util.Date;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>{
    private List<Doctor> doctors;
    final private DoctorAdapter.OnListItemClickListener listener;

    public DoctorAdapter(List<Doctor> doctors, DoctorAdapter.OnListItemClickListener listener) {
        this.listener = listener;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_doctor, parent, false);
        return new DoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {
        holder.name.setText(doctors.get(position).getName());
        holder.clinicName.setText(doctors.get(position).getClinicName());
        holder.address.setText(doctors.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, clinicName, address;
        private Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctorNameList);
            clinicName = itemView.findViewById(R.id.doctorClinicList);
            address = itemView.findViewById(R.id.doctorAddressList);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {
            listener.onListItemClick(getAdapterPosition());
        }
    }

    public interface OnListItemClickListener
    {
        void onListItemClick(int clickedItemIndex);
    }
}
