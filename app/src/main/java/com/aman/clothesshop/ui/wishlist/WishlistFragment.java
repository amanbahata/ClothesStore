package com.aman.clothesshop.ui.wishlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.clothesshop.R;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.services.ApiManager;
import com.aman.clothesshop.storage.SharedPrefManager;

import java.util.List;

public class WishlistFragment extends Fragment {

    private WishlistPresenter presenter;
    private RecyclerView recyclerView;
    private WishlistAdaptor adaptor;
    private List<Product> wishList;

    private final String EMPTY_LIST_MESSAGE = "Your wishlist is empty";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wishlist, container, false);

        recyclerView = root.findViewById(R.id.rvWishlistItems);
        presenter = new WishlistPresenter(this, new SharedPrefManager(
                this.getActivity().getSharedPreferences("com.aman.clothesshop.activities", Context.MODE_PRIVATE)),
                ApiManager.getInstance());
        presenter.getWishlist();

        return root;
    }

    void processResponse(List<Product> products) {
        wishList = products;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptor = new WishlistAdaptor(getContext(), wishList, new OnOptionClickedListener() {
            @Override
            public void onOptionClick(int itemId, int index, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.add_to_cart:
                    presenter.addToCart(itemId);
                    presenter.removeFromList(itemId);
                    updateRecyclerView(index);
                    break;
                case R.id.remove_from_list:
                    updateRecyclerView(index);
                    presenter.removeFromList(itemId);
                    break;
            }
            }
        });
        if (wishList.isEmpty()) {
            displayMessage(EMPTY_LIST_MESSAGE);
        }
        recyclerView.setAdapter(adaptor);
    }

    public void updateRecyclerView(int index) {
        wishList.remove(index);
        if (wishList.isEmpty()) {
            displayMessage(EMPTY_LIST_MESSAGE);
        }
        adaptor.updateRecyclerData(wishList);
    }

    void displayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}