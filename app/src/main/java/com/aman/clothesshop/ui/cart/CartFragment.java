package com.aman.clothesshop.ui.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aman.clothesshop.R;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.services.ApiManager;

import java.util.List;

public class CartFragment extends Fragment {

    private TableLayout tableLayout;
    private CartPresenter presenter;
    private List<Product> itemsList;
    private Button submitButton;
    private TextView cartTotalText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        tableLayout = root.findViewById(R.id.cart_items);
        submitButton = root.findViewById(R.id.submit_order);
        cartTotalText = root.findViewById(R.id.cart_total);

        presenter = new CartPresenter(this);
        itemsList = presenter.getCart();
        addItems(itemsList);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    private void addItems(final List<Product> items) {

        String removeText = "Remove";

        for(int i=0; i<items.size(); i++) {

            final Product item = items.get(i);
            final int index = i;

            final TableRow row = new TableRow(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);

            TextView name = new TextView(getContext());
            TextView price = new TextView(getContext());
            Button delete = new Button(getContext());

            delete.setText(removeText);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.remove(index);
                    presenter.deleteItem(row);
                    displayTotal(items);
                }
            });

            name.setText(item.getName());
            price.setText(String.format("%s",item.getPrice()));
            row.addView(name);
            row.addView(price);
            row.addView(delete);

            tableLayout.addView(row, i);
        }

        displayTotal(items);
        displaySubmitButton(items);
    }

    private void displayTotal(List<Product> items) {
        String total = String.valueOf(presenter.calculateTotal(items));
        cartTotalText.setText(total);
    }

    public void removeItem(TableRow row) {
        String itemRemovedText = "Item removed";
        tableLayout.removeView(row);
        Toast.makeText(getContext(), itemRemovedText, Toast.LENGTH_SHORT).show();
        displaySubmitButton(itemsList);
    }

    private void displaySubmitButton(List<Product> items) {
        if (!items.isEmpty()){
            if (submitButton.getVisibility() == View.GONE){
                submitButton.setVisibility(View.VISIBLE);
            }
        }else {
            if (submitButton.getVisibility() == View.VISIBLE) {
                submitButton.setVisibility(View.GONE);
            }
        }
    }
}