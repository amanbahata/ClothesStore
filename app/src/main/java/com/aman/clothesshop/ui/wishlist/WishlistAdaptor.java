package com.aman.clothesshop.ui.wishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.clothesshop.R;
import com.aman.clothesshop.model.response.Product;

import java.util.List;

class WishlistAdaptor extends RecyclerView.Adapter<WishlistAdaptor.ViewHolder> {

    private LayoutInflater inflater;
    private List<Product> wishList;
    private Context context;
    private OnOptionClickedListener listener;


    WishlistAdaptor(Context context, List<Product> wishList, OnOptionClickedListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.wishList = wishList;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.wishlist_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product listItem = wishList.get(position);
        holder.itemName.setText(listItem.getName());
        holder.menuOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.menuOptions);
                popup.inflate(R.menu.edit_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        listener.onOptionClick(listItem.getProductId(), position, item);
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    void updateRecyclerData(List<Product> wishList) {
        this.wishList = wishList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        ImageView menuOptions;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_title);
            menuOptions = itemView.findViewById(R.id.buttonOptions);
        }
    }
}
