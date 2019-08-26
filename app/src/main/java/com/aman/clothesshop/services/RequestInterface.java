package com.aman.clothesshop.services;


import com.aman.clothesshop.model.response.CartItem;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.model.response.Response;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestInterface {

    // Product related resources of the Shop API
    @GET("products")
    Observable<List<Product>> getAllProducts();

    @GET("products/{productId}")
    Observable<Product> getProductById(@Path("productId") String productId);

    // Shopping Cart related resources of the Shop API
    @POST("cart")
    Observable<CartItem> addProductToCart(@Body RequestBody prams);

    @DELETE("cart/{productId}")
    Observable<Response> removeProductFromCart(@Path("productId") String productId);
}
