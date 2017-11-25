package redo.tribaxy.com.customclasses;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import redo.tribaxy.com.R;
import redo.tribaxy.com.interfaces.RadioCheckable;

/**
 * Created by dalafiari on 11/24/17.
 */

public class PresetRadioSquareButton extends RelativeLayout implements RadioCheckable {

    //Views
    LinearLayout mBackgroundLayout;
    TextView mDayTextHolder;
    GradientDrawable gottenView;

    //Attribute values
    private int mDefaultTextColor = getResources().getColor(R.color.grey_500);
    private int mSelectedTextColor = getResources().getColor(R.color.regular_white);

    private String mTitle;

    // Variables
    private OnClickListener mOnClickListener;
    private OnTouchListener mOnTouchListener;
    private boolean mChecked;
    private ArrayList<OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();

    //================================================================================


    //================================================================================

    // Constructors

    public PresetRadioSquareButton(Context context) {
        super(context);
        setUpView();
    }

    public PresetRadioSquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setUpView();
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public PresetRadioSquareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setUpView();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PresetRadioSquareButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setUpView();
    }


    private void setUpView() {

        // always call these functions in this order, else nullPointerException will be thrown
        inflateView();
        bindView();
        setCustomTouchListener();
    }


    private void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.days_palette, this, true);
        mBackgroundLayout = (LinearLayout) findViewById(R.id.days_palette_display);
        mDayTextHolder = (TextView) findViewById(R.id.day_text_holder);

        // responsible for getting the colour on the square-button object
        gottenView = (GradientDrawable) mBackgroundLayout.getBackground();

    }


    private void parseAttributes(AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.PresetRadioSquareButton, 0, 0);
        try {
            // get the text passed down from the {@add_new_category.xml#preset_square_radio_button}
            // and store it globally.
            mTitle = a.getString(R.styleable.PresetRadioSquareButton_presetRadioSquareButtonText);
            mChecked = a.getBoolean(R.styleable.PresetRadioSquareButton_presetRadioSquareSetChecked, false);


        } finally {
            a.recycle();
        }

    }

    // responsible for setting the text on the text-view of the square-button object
    protected void bindView() {
        mDayTextHolder.setText(mTitle);
        setChecked(mChecked);

    }


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

    @Override
    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener);

    }

    @Override
    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener);

    }

    @Override
    public void setChecked(boolean checked) {

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


    public void setCheckedState() {


        //set the background of the selected one to @colorPrimary
        gottenView.setColor(getResources().getColor(R.color.colorPrimary));
        mDayTextHolder.setTextColor(mSelectedTextColor);

    }

    public void setNormalState() {

        //set the background of the selected one to @transparent
        gottenView.setColor(getResources().getColor(android.R.color.transparent));
        mDayTextHolder.setTextColor(mDefaultTextColor);

    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);

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
