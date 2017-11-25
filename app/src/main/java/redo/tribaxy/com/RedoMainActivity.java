package redo.tribaxy.com;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import redo.tribaxy.com.adapters.HomeCardItemAdapter;
import redo.tribaxy.com.models.HomeCardItem;

public class RedoMainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<List<HomeCardItem>>, View.OnClickListener {

    protected static long backPressed;
    private SwipeRefreshLayout swipeListRefresh;
    private List<HomeCardItem> mCardItems;
    private RecyclerView mHomeItemCardRecycler;
    private LinearLayout mNoRedoHolder;
    private HomeCardItemAdapter mCardItemAdapter;
    private FloatingActionButton mAddCategoryFab;

    protected int LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialise list object each time the #onCreate method is called.
        mCardItems = new ArrayList<>();

        setContentView(R.layout.home);

        getWindow().setBackgroundDrawable(null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialising the adapter for redo list display
        mCardItemAdapter = new HomeCardItemAdapter(this, new ArrayList<HomeCardItem>());

        // initialise the swipeRefreshLayout
        swipeListRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_redo_list);
        swipeListRefresh.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        swipeListRefresh.setOnRefreshListener(this);

        //initialise the FAB that handles creation of new Categories
        mAddCategoryFab = (FloatingActionButton) findViewById(R.id.home_add_category);
        mAddCategoryFab.setOnClickListener(this);

        //initialising the No Redo view holder
        mNoRedoHolder = (LinearLayout) findViewById(R.id.no_category_display);

        // initialise the recycler to be used
        mHomeItemCardRecycler = (RecyclerView) findViewById(R.id.home_card_item_recycler_view);
        mHomeItemCardRecycler.setItemAnimator(new DefaultItemAnimator());
        mHomeItemCardRecycler.setHasFixedSize(true);
        mHomeItemCardRecycler.setAdapter(mCardItemAdapter);

        //creating the layout manager to be used by the recycler
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mHomeItemCardRecycler.setLayoutManager(layoutManager);


        // requesting the support loader manager
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);


    }


    @Override
    public void onRefresh() {

        swipeListRefresh.setRefreshing(true);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    }


    @Override
    public Loader<List<HomeCardItem>> onCreateLoader(int id, Bundle args) {
        return new FetchRedoList(this);
    }

    @Override
    public void onLoadFinished(Loader<List<HomeCardItem>> loader, List<HomeCardItem> data) {

        //release the refresh UI
        swipeListRefresh.setRefreshing(false);

        if (mCardItems.isEmpty()) {
            mHomeItemCardRecycler.setVisibility(View.GONE);
            mNoRedoHolder.setVisibility(View.VISIBLE);

        } else {

            mCardItemAdapter.setHomeCardItems(data);
            mHomeItemCardRecycler.setVisibility(View.VISIBLE);
            mNoRedoHolder.setVisibility(View.GONE);

        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

        mCardItemAdapter.setHomeCardItems(new ArrayList<HomeCardItem>());

    }


    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else {
            Toast.makeText(this, getString(R.string.exit_alert), Toast.LENGTH_SHORT).show();
            backPressed = System.currentTimeMillis();
        }

    }

    @Override
    public void onClick(View view) {

    }


    private static class FetchRedoList extends AsyncTaskLoader<List<HomeCardItem>> {

        FetchRedoList(Context context) {
            super(context);
        }

        @Override
        public List<HomeCardItem> loadInBackground() {
            return new ArrayList<>();
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }

}
