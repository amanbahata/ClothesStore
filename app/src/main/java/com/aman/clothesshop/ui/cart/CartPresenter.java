package com.aman.clothesshop.ui.cart;

import android.widget.TableRow;

import com.aman.clothesshop.model.response.Product;

import java.util.ArrayList;
import java.util.List;
public class CartPresenter {

    private CartFragment view;

    public CartPresenter(CartFragment cartFragment) {
        this.view = cartFragment;
    }


    /**
     * Method to mock model response from cart api
     * @return
     */
    public List<Product> getCart() {

        // Test Cart objects
        List<Product> cartItemsList = new ArrayList<>();

        Product itemOne = new Product();
        itemOne.setProductId(1);
        itemOne.setName("Almond Toe Court Shoes, Patent Black");
        itemOne.setPrice(99d);

        Product itemTwo = new Product();
        itemTwo.setProductId(2);
        itemTwo.setName("Suede Shoes, Blue");
        itemTwo.setPrice(42d);

        cartItemsList.add(itemOne);
        cartItemsList.add(itemTwo);


        return cartItemsList;
    }


    public void deleteItem(TableRow row) {
        view.removeItem(row);
    }

    public Double calculateTotal(List<Product> items) {
        Double total = 0d;

        if (items != null) {
            for (Product item : items) {
                total += item.getPrice();
            }
        }
        return total;
    }
}