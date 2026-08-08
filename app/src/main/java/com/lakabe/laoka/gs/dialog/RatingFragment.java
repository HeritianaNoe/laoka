package com.lakabe.laoka.gs.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.interfaces.FoodListener;
import com.lakabe.laoka.gs.model.Food;

public class RatingFragment extends DialogFragment {
    private static Food food;
    private static FoodListener listener;

    private ImageView btn_minus;
    private ImageView btn_plus;
    private TextView txt_number;
    private Button okButton;
    private Button cancelButton;
    private int rating;
    private static int pos;

    public RatingFragment() {
        // Required empty public constructor
    }

    public static RatingFragment getInstance(Food f, int p, FoodListener l) {
        food = f;
        listener = l;
        pos = p;
        RatingFragment fragment = new RatingFragment();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.LaokaDialog);
        fragment.setCancelable(false);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_rating, container, false);

        rating = food.getRating();

        txt_number = rootView.findViewById(R.id.txt_number);
        txt_number.setText(String.valueOf(rating));

        btn_minus = rootView.findViewById(R.id.btn_minus);
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rating > 0){
                    rating--;
                    //food.setRating(rating);
                    txt_number.setText(String.valueOf(rating));
                }
            }
        });
        btn_plus = rootView.findViewById(R.id.btn_plus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rating < 5){
                    rating++;
                    //food.setRating(rating);
                    txt_number.setText(String.valueOf(rating));
                }
            }
        });

        okButton = rootView.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food f = new Food(food.getName(),food.getDesc(),food.getPhoto(),food.getHeart(),rating,food.getCategoryId());
                f.setId(food.getId());
                listener.onRatingSelected(f, pos, view);
                getDialog().dismiss();
            }
        });
        cancelButton = rootView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }
}