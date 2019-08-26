package com.aman.clothesshop.storage;

import com.aman.clothesshop.model.response.Product;

import java.util.List;

public interface ISharedPrefsManager {

    List<Product> getWishList();

    void saveItemToWishlist(Product response);

    void removeItemFromWishlist(int id);
}
