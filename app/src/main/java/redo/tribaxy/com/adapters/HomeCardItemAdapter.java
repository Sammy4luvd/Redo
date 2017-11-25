package redo.tribaxy.com.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import redo.tribaxy.com.R;
import redo.tribaxy.com.interfaces.DeleteAndRestorable;
import redo.tribaxy.com.models.HomeCardItem;
import redo.tribaxy.com.utils.TextUtils;

/**
 * Created by dalafiari on 11/17/17.
 */

public class HomeCardItemAdapter extends RecyclerView.Adapter<HomeCardItemAdapter.HomeCardItemViewHolder> implements DeleteAndRestorable {
    private List<HomeCardItem> mHomeCardItems;
    private Context mContext;


    public HomeCardItemAdapter(Context context, List<HomeCardItem> list) {
        this.mContext = context;
        this.mHomeCardItems = list;
    }


    @Override
    public HomeCardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_card, parent, false);

        return new HomeCardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeCardItemViewHolder holder, int position) {
        final HomeCardItem cardItem = mHomeCardItems.get(position);
        holder.categoryId = cardItem.getCategoryId();
        holder.categoryTitle.setText(cardItem.getCategoryTitle());
        holder.categoryItemCount.setText(TextUtils.getItemCountPlural(mContext, cardItem.getCategoryCount()));
        holder.categoryColor.setBackgroundColor(Color.parseColor(cardItem.getCategoryColour()));

        holder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open a single category activity
            }
        });
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // open the bottom-sheet interface
                return false;
            }

        });


    }

    public void setHomeCardItems(List<HomeCardItem> list) {
        this.mHomeCardItems = list;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mHomeCardItems.size();
    }

    @Override
    public void removeItem(int position) {
        mHomeCardItems.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public void restoreItem(Object item, int position) {
        if (item instanceof HomeCardItem) {
            mHomeCardItems.add(position, (HomeCardItem) item);
            notifyItemInserted(position);
        }

    }

    class HomeCardItemViewHolder extends RecyclerView.ViewHolder {

        UUID categoryId;
        CardView container;
        TextView categoryTitle;
        TextView categoryItemCount;
        View categoryColor;

        HomeCardItemViewHolder(View view) {
            super(view);
            container = (CardView) view.findViewById(R.id.home_card_item);
            categoryTitle = (TextView) view.findViewById(R.id.home_card_item_title);
            categoryItemCount = (TextView) view.findViewById(R.id.home_card_item_count);
            categoryColor = view.findViewById(R.id.home_card_item_category_colour);
        }

    }
}
