package com.aman.clothesshop.storage;

import android.content.SharedPreferences;

import com.aman.clothesshop.model.response.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPrefManager implements ISharedPrefsManager {

    private final SharedPreferences preferences;
    private final Gson gson = new Gson();

    public SharedPrefManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }


    @Override
    public List<Product> getWishList() {
        List<Product> productList = new ArrayList<>();
        Map<String, ?> productDetail = preferences.getAll();
        for (Map.Entry<String, ?> entry : productDetail.entrySet()) {
            Product product = gson.fromJson(entry.getValue().toString(), Product.class);
            productList.add(product);
        }
        return productList;
    }

    @Override
    public void saveItemToWishlist(Product product) {
        String convertedId = String.valueOf(product.getProductId());
        preferences.edit().putString(convertedId, gson.toJson(product)).apply();
    }

    @Override
    public void removeItemFromWishlist(int id) {
        String convertId = String.valueOf(id);
        preferences.edit().remove(convertId).apply();
    }
}
