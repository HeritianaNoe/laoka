package com.lakabe.laoka.gs.interfaces;

import android.view.View;

import com.lakabe.laoka.gs.model.Food;

public interface FoodListener {
    void onHeartSelected(Food food, int pos, View view);
    void onRatingSelected(Food food, int pos, View view);
    void onSelected(Food food, int pos, View view);
}
