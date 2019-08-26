package com.aman.clothesshop.services;


import com.aman.clothesshop.model.request.AddProductRequest;
import com.aman.clothesshop.model.response.CartItem;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.model.response.Response;

import java.util.List;

import io.reactivex.Observable;

public interface ApiHelper {

    Observable<List<Product>> getAllProducts();

    Observable<Product> getProductById(String productId);

    Observable<CartItem> addProductToCart(AddProductRequest request);

    Observable<Response> removeProductFromCart(String productId);
}
