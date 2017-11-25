package redo.tribaxy.com.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import redo.tribaxy.com.R;

/**
 * Created by dalafiari on 11/23/17.
 */

public class AddNewCategoryActivity extends AppCompatActivity {

    private static boolean editFlag = false;
    private ActionBar actionBar;

    public static Intent newAddCategoryIntent(Context context) {

        return new Intent(context, AddNewCategoryActivity.class);
    }

    public static Intent newEditCategoryIntent(Context context, Bundle bundle) {
        Intent intent = newAddCategoryIntent(context);
        intent.putExtras(bundle);
        editFlag = true;
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_category);

        getWindow().setBackgroundDrawable(null);

        // initialise the toolbar, replace the default with the defined
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the supportActionBar, perform necessary actions
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
