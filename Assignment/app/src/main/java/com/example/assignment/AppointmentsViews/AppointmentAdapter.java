package com.example.assignment.AppointmentsViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.Shared.DateTimeFormat;
import com.example.assignment.localDb.AppointmentDB;

import java.util.Date;
import java.util.List;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder>{
    private List<AppointmentDB> appointments;
    final private OnListItemClickListener listener;

    public AppointmentAdapter(List<AppointmentDB> appointments, OnListItemClickListener listener) {
        this.listener = listener;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.one_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.label.setText(appointments.get(position).getLabel());
        holder.details.setText(appointments.get(position).getDetails());
        Date d = appointments.get(position).getDate();

        String s = DateTimeFormat.formatDateTime(d);
        holder.dateTime.setText(s);
        if(appointments.get(position).getCancelled())
        {
            holder.label.setTextColor(holder.context.getResources().getColor(R.color.cancelled));
            holder.details.setTextColor(holder.context.getResources().getColor(R.color.cancelled));
            holder.dateTime.setTextColor(holder.context.getResources().getColor(R.color.cancelled));
        }
        else {
            holder.label.setTextColor(holder.context.getResources().getColor(R.color.colorPrimary));
            holder.details.setTextColor(holder.context.getResources().getColor(R.color.colorPrimaryDark));
            holder.dateTime.setTextColor(holder.context.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView label, details, dateTime;
        private Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.labelAppointment);
            details = itemView.findViewById(R.id.detailsAppointment);
            dateTime = itemView.findViewById(R.id.dateAndTimeAppointment);
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
