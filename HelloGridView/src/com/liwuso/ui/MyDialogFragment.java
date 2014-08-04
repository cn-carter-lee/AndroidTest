package com.liwuso.ui;

import android.app.AlertDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.liwuso.app.R;

public class MyDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.dialog, null));

		Dialog d = builder.create();
		
		d.requestWindowFeature(STYLE_NO_FRAME);
		d.requestWindowFeature(STYLE_NO_INPUT);
		return d;
	}
}