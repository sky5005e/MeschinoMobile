package com.trisoft.ecommerce_new.barcodescanner.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.trisoft.ecommerce_new.R;


public class LicenseFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        View openSourceLicensesView = dialogInflater.inflate(R.layout.fragment_license, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(openSourceLicensesView)
                .setTitle((getString(R.string.dialog_title_licenses)))
                .setNeutralButton(android.R.string.ok, null);

        return dialogBuilder.create();
    }
}
