package redo.tribaxy.com.customclasses;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import redo.tribaxy.com.R;
import redo.tribaxy.com.interfaces.RadioCheckable;

/**
 * Created by dalafiari on 11/20/17.
 */

public class PresetRadioCircleButton extends RelativeLayout implements RadioCheckable {
    //Views
    private ImageView mSelectedDoneDrawable;
    private LinearLayout mCircleShapeLayout;

    //Attribute values
    private int mColor;


    // Variables
    private OnClickListener mOnClickListener;
    private OnTouchListener mOnTouchListener;
    private boolean mChecked;
    private ArrayList<OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();

    //================================================================================
    // Constructors
    //================================================================================

    public PresetRadioCircleButton(Context context) {
        super(context);
        setupView();
    }

    public PresetRadioCircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public PresetRadioCircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }


    //probably not needed in this particular case
    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.PresetRadioCircleButton, 0, 0);
        Resources resources = getContext().getResources();

        try {
            // get the colour passed down from the {@add_new_category.xml#preset_circle_radio_group}
            // and store it globally, default colorPrimary is set if no value was given
            mColor = a.getColor(R.styleable.PresetRadioCircleButton_presetRadioCircleColor,
                    resources.getColor(R.color.colorPrimary));


        } finally {
            a.recycle();
        }

    }

    private void setupView() {

        // always call these functions in this order, else nullPointerException will be thrown
        inflateView();
        bindView();
        setCustomTouchListener();

    }

    protected void inflateView() {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.color_palette, this, true);
        mSelectedDoneDrawable = (ImageView) findViewById(R.id.selected_color_indicator);
        mCircleShapeLayout = (LinearLayout) findViewById(R.id.color_palette_display);

    }

    // responsible for changing the colour on the circle object
    protected void bindView() {

        GradientDrawable gottenView = (GradientDrawable) mCircleShapeLayout.getBackground();
        gottenView.setColor(mColor);

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    //================================================================================
    // Overriding default behavior
    //================================================================================

    private void setCustomTouchListener() {
        super.setOnTouchListener(new TouchListener());


    }

    public OnTouchListener getOnTouchListener() {
        return mOnTouchListener;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        setChecked(true);
    }

    private void onTouchUp(MotionEvent motionEvent) {
        // Handle user defined click listeners
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
        }
    }

    //================================================================================
    // Public methods
    //================================================================================

    public void setCheckedState() {
        mSelectedDoneDrawable.setVisibility(View.VISIBLE);

    }

    public void setNormalState() {
        mSelectedDoneDrawable.setVisibility(View.GONE);

    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int value) {
        mColor = value;
    }

    //================================================================================
    // Checkable implementation
    //================================================================================

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            if (!mOnCheckedChangeListeners.isEmpty()) {
                for (int i = 0; i < mOnCheckedChangeListeners.size(); i++) {
                    mOnCheckedChangeListeners.get(i).onCheckedChanged(this, mChecked);
                }
            }
            if (mChecked) {
                setCheckedState();
            } else {
                setNormalState();
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener);
    }

    @Override
    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener);
    }


    //================================================================================
    // Inner classes
    //================================================================================
    private final class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onTouchDown(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onTouchUp(event);
                    break;
            }
            if (mOnTouchListener != null) {
                mOnTouchListener.onTouch(v, event);
            }
            return true;

            /*return v.performClick();*/
        }


    }
}

