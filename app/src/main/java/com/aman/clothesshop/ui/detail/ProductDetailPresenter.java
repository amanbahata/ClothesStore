package com.aman.clothesshop.ui.detail;

import com.aman.clothesshop.model.request.AddProductRequest;
import com.aman.clothesshop.model.response.CartItem;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.services.ApiHelper;
import com.aman.clothesshop.services.ApiManager;
import com.aman.clothesshop.storage.ISharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class ProductDetailPresenter {

    private ApiHelper apiInterface;
    private ProductDetailFragment view;
    private ISharedPrefsManager sharedPref;

    ProductDetailPresenter(ProductDetailFragment productDetailFragment, ApiManager apiInterface,
                           ISharedPrefsManager sharedPref) {
        this.view = productDetailFragment;
        this.apiInterface = apiInterface;
        this.sharedPref = sharedPref;
    }


    void getProductById(int productId) {

        String convertedId = String.valueOf(productId);
        apiInterface.getProductById(convertedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Product>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Product product) {
                        view.displayItem(product);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    void addItemToWishList(Product product) {
        sharedPref.saveItemToWishlist(product);
        view.displayMessage("Item added to wishlist");

    }

    void addItemToCart(int productId) {
        AddProductRequest request = new AddProductRequest(productId);
        apiInterface.addProductToCart(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CartItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CartItem cartItem) {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        view.displayMessage("Item added to basket");
                    }
                });
    }

    List<String> separateProductNameFromColor(String productName) {

        List<String> info = new ArrayList<>();

        int index = productName.indexOf(",");
        String name = productName.substring(0, index).trim();
        String color = productName.substring(index + 1).trim();

        info.add(name);
        info.add(color);

        return info;
    }


}
