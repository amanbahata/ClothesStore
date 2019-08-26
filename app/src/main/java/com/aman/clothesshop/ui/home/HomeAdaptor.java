package com.aman.clothesshop.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.clothesshop.R;
import com.aman.clothesshop.model.response.Product;

import java.util.List;


public class HomeAdaptor extends RecyclerView.Adapter<HomeAdaptor.ViewHolder> {

    private LayoutInflater inflater;
    private List<Product> productsList;
    private OnItemClickListener listener;
    public HomeAdaptor(Context context, List<Product> productsList, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.productsList = productsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(productsList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void updateRecyclerData(List<Product> feedItemList) {
        this.productsList = feedItemList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Product item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            productImage = itemView.findViewById(R.id.ivProductImage);
        }


        public void bind(final Product item, final OnItemClickListener listener) {
            productName.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
