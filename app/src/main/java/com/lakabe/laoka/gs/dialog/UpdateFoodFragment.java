package com.lakabe.laoka.gs.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.interfaces.UpdateFoodListener;

public class UpdateFoodFragment extends AppCompatDialogFragment {
    private static UpdateFoodListener listener;
    private static final String ARG_PARAM1 = "param1";

    private EditText mText;

    private String mParam1;

    public UpdateFoodFragment() {
        // Required empty public constructor
    }

    public static UpdateFoodFragment getInstance(String param1, UpdateFoodListener l) {
        listener = l;
        UpdateFoodFragment fragment = new UpdateFoodFragment();
        fragment.setCancelable(false);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_update_food,null);

        builder.setView(view);
        builder.setCancelable(false);
        builder.setTitle("FANAZAVANA");

        mText = view.findViewById(R.id.txt_desc);
        mText.setText(mParam1);

        builder.setPositiveButton("Eny", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                listener.onDescription(mText.getText().toString());
                dismiss();
            }
        });

        builder.setNegativeButton("Tsia", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dismiss();
            }
        });

        return builder.create();
    }
}