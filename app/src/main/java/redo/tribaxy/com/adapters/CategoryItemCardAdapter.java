package redo.tribaxy.com.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import redo.tribaxy.com.R;
import redo.tribaxy.com.interfaces.DeleteAndRestorable;
import redo.tribaxy.com.models.CategoryItemCard;

/**
 * Created by dalafiari on 11/18/17.
 */

public class CategoryItemCardAdapter extends RecyclerView.Adapter<CategoryItemCardAdapter.CategoryItemCardHolder> implements DeleteAndRestorable {
    private Context mContext;
    private List<CategoryItemCard> mCategoryItemCards;


    public CategoryItemCardAdapter(Context context, List<CategoryItemCard> list) {
        this.mContext = context;
        this.mCategoryItemCards = list;
    }


    @Override
    public CategoryItemCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_card, parent, false);
        return new CategoryItemCardHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryItemCardHolder holder, int position) {
        CategoryItemCard itemCard = mCategoryItemCards.get(position);
        holder.id = itemCard.getId();
        holder.mCategoryItemTitle.setText(itemCard.getTitle());
        holder.mCategoryItemDragHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    public void setCategoryItemCards(List<CategoryItemCard> list) {
        this.mCategoryItemCards = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCategoryItemCards.size();
    }


    @Override
    public void removeItem(int position) {
        mCategoryItemCards.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void restoreItem(Object item, int position) {
        if (item instanceof CategoryItemCard) {
            mCategoryItemCards.add(position, (CategoryItemCard) item);
            notifyItemInserted(position);
        }
    }

    public class CategoryItemCardHolder extends RecyclerView.ViewHolder {

        public RelativeLayout viewForeground;
        public LinearLayout viewBackground;

        int id;
        CardView mCategoryItemCard;
        ImageView mCategoryItemDragHandle;
        TextView mCategoryItemTitle;

        CategoryItemCardHolder(View view) {
            super(view);

            mCategoryItemCard = (CardView) view.findViewById(R.id.category_item_card);
            mCategoryItemDragHandle = (ImageView) view.findViewById(R.id.item_drag_handle);
            mCategoryItemTitle = (TextView) view.findViewById(R.id.category_item_title);

            viewForeground = (RelativeLayout) view.findViewById(R.id.view_foreground);
            viewBackground = (LinearLayout) view.findViewById(R.id.view_background);

        }
    }
}
