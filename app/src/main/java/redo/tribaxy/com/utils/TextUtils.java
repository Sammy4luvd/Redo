package redo.tribaxy.com.utils;

import android.content.Context;

import redo.tribaxy.com.R;

/**
 * Created by dalafiari on 11/17/17.
 */

public class TextUtils {

    public static String getItemCountPlural(Context context, int size) {
        return context.getResources().getQuantityString(R.plurals.item_count_plurals, size, size);
    }
}
