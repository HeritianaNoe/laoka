package com.lakabe.laoka.gs.interfaces;

import android.view.View;

import com.lakabe.laoka.gs.model.Fady;

public interface FadyListener {
    void onInsert(Fady fady, int pos, View view);
    void onUpdate(Fady fady, int pos,  View view);
    void onDelete(Fady fady, int pos,  View view);
}
