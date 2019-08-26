package com.aman.clothesshop.ui.home;

import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.services.ApiHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class HomePresenter {

    private ApiHelper apiInterface;
    private HomeFragment view;

    public HomePresenter(HomeFragment view, ApiHelper apiInterface) {
        this.apiInterface = apiInterface;
        this.view = view;

    }

    public void getAllProducts() {
        apiInterface.getAllProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Product>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        view.processResponse(products);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.processError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public List<Product> filterByCategory(List<Product> productListToFilter, String category) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productListToFilter) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }
}