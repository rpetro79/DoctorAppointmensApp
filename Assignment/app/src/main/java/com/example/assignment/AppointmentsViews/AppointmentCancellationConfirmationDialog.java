package com.example.assignment.AppointmentsViews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.assignment.R;

public class AppointmentCancellationConfirmationDialog extends DialogFragment {
    private AppointmentCancellationConfirmationDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        listener = (AppointmentCancellationConfirmationDialogListener) getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.cancelAppointmentPrompt)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.cancel();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface AppointmentCancellationConfirmationDialogListener
    {
        void cancel();
    }
}
