package com.aman.clothesshop.services;


import com.aman.clothesshop.model.request.AddProductRequest;
import com.aman.clothesshop.model.response.CartItem;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.model.response.Response;

import java.util.List;

import io.reactivex.Observable;

public class ApiManager implements ApiHelper {

    private static ApiManager instance;
    private RequestInterface requestInterface;

    private ApiManager() {
        this.requestInterface = ServerConnection.getClient();
    }

    public static ApiManager getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new ApiManager();
        return instance;
    }

    @Override
    public Observable<List<Product>> getAllProducts() {
        return requestInterface.getAllProducts();
    }

    @Override
    public Observable<Product> getProductById(String productId) {
        return requestInterface.getProductById(productId);
    }

    @Override
    public Observable<CartItem> addProductToCart(AddProductRequest request) {
        return requestInterface.addProductToCart(request.getRequestBody());
    }

    @Override
    public Observable<Response> removeProductFromCart(String productId) {
        return requestInterface.removeProductFromCart(productId);
    }
}
