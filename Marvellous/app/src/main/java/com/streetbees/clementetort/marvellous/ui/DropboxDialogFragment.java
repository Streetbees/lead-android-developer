package com.streetbees.clementetort.marvellous.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.streetbees.clementetort.marvellous.R;

/**
 * Created by clemente.tort on 20/04/16.
 */
public class DropboxDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private DropboxDialogFragmentListener listener;

    public static DropboxDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DropboxDialogFragment fragment = new DropboxDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(!(activity instanceof DropboxDialogFragmentListener))
            throw new IllegalStateException("Activity must implement DropboxDialogFragmentListener");

        this.listener = (DropboxDialogFragmentListener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.login_on_dropbox)
                .setPositiveButton(android.R.string.ok, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        listener.onDropboxLogin();
    }

    public interface DropboxDialogFragmentListener {
        void onDropboxLogin();
    }

}
