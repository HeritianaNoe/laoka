package com.lakabe.laoka.gs.interfaces;

import android.view.View;

import com.lakabe.laoka.gs.model.Category;

public interface CategoryListener {
    void onSelected(Category category, int pos, View view);
}
