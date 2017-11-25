package redo.tribaxy.com.interfaces;

import android.view.View;
import android.widget.Checkable;

/**
 * Created by dalafiari on 11/19/17.
 */

public interface RadioCheckable extends Checkable {

    void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    interface OnCheckedChangeListener {
        void onCheckedChanged(View buttonView, boolean isChecked);
    }
}
