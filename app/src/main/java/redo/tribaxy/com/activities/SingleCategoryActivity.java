package redo.tribaxy.com.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import redo.tribaxy.com.R;
import redo.tribaxy.com.adapters.CategoryItemCardAdapter;
import redo.tribaxy.com.customclasses.RecyclerItemTouchHelper;
import redo.tribaxy.com.models.CategoryItemCard;
import redo.tribaxy.com.models.HomeCardItem;

/**
 * Created by dalafiari on 11/21/17.
 */

public class SingleCategoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, LoaderManager.LoaderCallbacks<List<CategoryItemCard>>, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private CoordinatorLayout mCoordinatorLayout;
    private FloatingActionButton mNewItemFab;
    private SwipeRefreshLayout swipeListRefresh;
    private RecyclerView mItemsRecyclerView;
    private LinearLayout mNoItemsViewLayout;
    private List<CategoryItemCard> mItemCardList;
    private CategoryItemCardAdapter mCardItemAdapter;

    private String gottenColor;
    private String gottenTitle;
    private UUID gottenId;

    private int DEFAULT_COLOR_IF_NONE = getResources().getColor(R.color.colorPrimary);
    private String DEFAULT_TITLE_IF_NONE = getResources().getString(R.string.default_category_title_if_none);

    protected int LOADER_ID = 2;


    public static Intent newIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SingleCategoryActivity.class);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_category_activity);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        mItemCardList = new ArrayList<>();

        //get Bundle passed to this activity
        Bundle gottenBundle = getIntent().getExtras();

        // parse the values sent and store them globally
        if (gottenBundle != null) {
            gottenColor = gottenBundle.getString(HomeCardItem.CATEGORY_COLOUR);
            gottenTitle = gottenBundle.getString(HomeCardItem.CATEGORY_TITLE);
            gottenId = UUID.fromString(gottenBundle.getString(HomeCardItem.CATEGORY_ID));
        }


        //clear unnecessary background colour
        getWindow().setBackgroundDrawable(null);

        // initialise the toolbar, replace the default with the defined
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set the toolbar colour with the category's
        if (TextUtils.isEmpty(gottenColor)) toolbar.setBackgroundColor(DEFAULT_COLOR_IF_NONE);
        else toolbar.setBackgroundColor(Color.parseColor(gottenColor));

        // set the toolbar title with the category's
        if (TextUtils.isEmpty(gottenTitle)) toolbar.setTitle(DEFAULT_TITLE_IF_NONE);
        else toolbar.setTitle(gottenTitle);


        // get the supportActionBar, perform necessary actions
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        // initialising the adapter for category list display
        mCardItemAdapter = new CategoryItemCardAdapter(this, new ArrayList<CategoryItemCard>());

        // initialise the swipeRefreshLayout
        swipeListRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_redo_list);
        swipeListRefresh.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        swipeListRefresh.setOnRefreshListener(this);

        //initialise the FAB that handles creation of new Category Items
        mNewItemFab = (FloatingActionButton) findViewById(R.id.category_item_add);
        mNewItemFab.setOnClickListener(this);

        //initialising the No Items view holder
        mNoItemsViewLayout = (LinearLayout) findViewById(R.id.no_category_display);

        // initialise the recycler to be used
        mItemsRecyclerView = (RecyclerView) findViewById(R.id.category_items_recycler_view);
        mItemsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mItemsRecyclerView.setHasFixedSize(true);
        mItemsRecyclerView.setAdapter(mCardItemAdapter);

        //creating the layout manager to be used by the recycler
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mItemsRecyclerView.setLayoutManager(layoutManager);


        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mItemsRecyclerView);

        // requesting the support loader manager
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {

        swipeListRefresh.setRefreshing(true);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
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

    @Override
    public Loader<List<CategoryItemCard>> onCreateLoader(int id, Bundle args) {
        return new FetchCategoryItems(this);
    }

    @Override
    public void onLoadFinished(Loader<List<CategoryItemCard>> loader, List<CategoryItemCard> data) {

        swipeListRefresh.setRefreshing(false);
        mCardItemAdapter.setCategoryItemCards(data);

    }

    @Override
    public void onLoaderReset(Loader<List<CategoryItemCard>> loader) {

        mCardItemAdapter.setCategoryItemCards(new ArrayList<CategoryItemCard>());

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof CategoryItemCardAdapter.CategoryItemCardHolder) {

            // backup of removed item for undo purpose
            final int deletedItemIndex = viewHolder.getAdapterPosition();
            final CategoryItemCard item = mItemCardList.get(deletedItemIndex);


            // get the removed item name to display it in snack bar
            String name = item.getTitle();

            // remove the item from recycler view
            mItemCardList.remove(deletedItemIndex);

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar.make(mCoordinatorLayout,
                    name + getResources().getString(R.string.delete_success_for_category), Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.undo_text, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCardItemAdapter.restoreItem(item, deletedItemIndex);
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.yellow_500));
            snackbar.show();


        }

    }

    private static class FetchCategoryItems extends AsyncTaskLoader<List<CategoryItemCard>> {

        FetchCategoryItems(Context context) {
            super(context);
        }

        @Override
        public List<CategoryItemCard> loadInBackground() {
            return null;

        }


        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}
