package com.digua.displayapp.way2;

import android.content.Context;
import android.view.Display;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

/**
 * TODO:describe what the class or interface does.
 *
 * @author RunningDigua
 * @date 2020/10/28
 */
public class DisplayListAdater extends ArrayAdapter<Display> {
    public DisplayListAdater(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
