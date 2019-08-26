package com.aman.clothesshop.ui.detail;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aman.clothesshop.R;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.services.ApiManager;
import com.aman.clothesshop.storage.SharedPrefManager;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {

    private final String PRODUCT_ID = "productId";
    private ProductDetailPresenter presenter;
    private TextView productName;
    private TextView productPriceDiscounted;
    private TextView productPrice;
    private TextView productQuantity;
    private TextView productColor;
    private TextView productCategory;
    private TextView addToWishlistButton;
    private TextView addToBasketButton;

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);

        productName = root.findViewById(R.id.product_name);
        productPriceDiscounted = root.findViewById(R.id.product_price_discounted);
        productPrice = root.findViewById(R.id.product_price);
        productQuantity = root.findViewById(R.id.product_quantity);
        productColor = root.findViewById(R.id.product_color);
        productCategory = root.findViewById(R.id.product_category);
        addToWishlistButton = root.findViewById(R.id.add_to_wishlist);
        addToBasketButton = root.findViewById(R.id.add_to_basket);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int productId = bundle.getInt(PRODUCT_ID, 1);

            presenter = new ProductDetailPresenter(this, ApiManager.getInstance(),
                    new SharedPrefManager(this.getActivity()
                            .getSharedPreferences("com.aman.clothesshop.activities", Context.MODE_PRIVATE)));

            presenter.getProductById(productId);
        }

        return root;
    }

    public void displayItem(final Product product) {
        final int productId = product.getProductId();
        final int quantity = product.getStock();
        List<String> productInfo = presenter.separateProductNameFromColor(product.getName());
        productName.setText(productInfo.get(0));
        productPrice.setText(String.format("%s%s", product.getPrice(), getString(R.string.currency)));
        productQuantity.setText(String.format("%s%d", getString(R.string.quantity_left), quantity));
        productColor.setText(String.format("%s%s", getString(R.string.color), productInfo.get(1)));
        productCategory.setText(String.format("%s%s", getString(R.string.category), product.getCategory()));

        if (product.getOldPrice() != null) {
            productPriceDiscounted.setVisibility(View.VISIBLE);
            productPriceDiscounted.setText(String.valueOf(product.getOldPrice()));
            productPriceDiscounted.setPaintFlags(productPriceDiscounted.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        addToWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToWishlist(product);
            }
        });

        if (quantity > 0) {
            addToBasketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToBasket(productId);
                }
            });
        } else {
            addToBasketButton.setEnabled(false);
        }
    }

    private void addToWishlist(Product product) {
        presenter.addItemToWishList(product);
    }

    private void addToBasket(int productId) {
        presenter.addItemToCart(productId);
    }

    public void displayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
