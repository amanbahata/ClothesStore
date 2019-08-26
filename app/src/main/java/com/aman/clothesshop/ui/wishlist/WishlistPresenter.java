package com.aman.clothesshop.ui.wishlist;

import com.aman.clothesshop.model.request.AddProductRequest;
import com.aman.clothesshop.model.response.CartItem;
import com.aman.clothesshop.services.ApiHelper;
import com.aman.clothesshop.storage.ISharedPrefsManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class WishlistPresenter {


    private WishlistFragment view;
    private ISharedPrefsManager sharedPref;
    private ApiHelper apiInterface;

    WishlistPresenter(WishlistFragment wishlistFragment, ISharedPrefsManager sharedPrefManager, ApiHelper apiInterface) {
        this.view = wishlistFragment;
        this.sharedPref = sharedPrefManager;
        this.apiInterface = apiInterface;
    }

    void getWishlist() {
        view.processResponse(sharedPref.getWishList());
    }

    void removeFromList(int itemId) {
        sharedPref.removeItemFromWishlist(itemId);
    }

    void addToCart(int itemId) {
        AddProductRequest request = new AddProductRequest(itemId);
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
                        view.displayMessage("Item moved to cart");
                    }
                });
    }
}