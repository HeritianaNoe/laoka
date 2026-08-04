package com.lakabe.laoka.gs.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lakabe.laoka.gs.MainActivity;
import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.interfaces.FadyListener;
import com.lakabe.laoka.gs.model.Fady;
import com.lakabe.laoka.gs.utils.WordUtils;

public class UpdateFadyFragment extends AppCompatDialogFragment {

    private EditText editText;
    private static FadyListener listener;
    private static Fady fady;
    private static int pos;

    public UpdateFadyFragment() {
        // Required empty public constructor
    }

    public static UpdateFadyFragment getInstance(Fady f, int p, FadyListener l) {
        fady = f;
        pos = p;
        listener = l;
        UpdateFadyFragment fragment = new UpdateFadyFragment();
        fragment.setCancelable(false);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fady_update,null);

        builder.setView(view);
        builder.setCancelable(false);
        builder.setTitle("FADY");

        editText = view.findViewById(R.id.editText);
        editText.setText(fady.getName());
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    handled = true;
                }

                return handled;
            }
        });

        builder.setPositiveButton("Eny", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String s = editText.getText().toString();
                Fady f = new Fady(WordUtils.Capitalize(s));
                f.setId(fady.getId());
                if(!s.isEmpty())
                    listener.onUpdate(f, pos, view);
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