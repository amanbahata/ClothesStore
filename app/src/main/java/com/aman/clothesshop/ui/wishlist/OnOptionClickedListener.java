package com.aman.clothesshop.ui.wishlist;


import android.view.MenuItem;

public interface OnOptionClickedListener {
    //    void onOptionAddToCartClick(int itemId, int index);
//    void onOptionRemoveFromListClick(int itemId, int index);
    void onOptionClick(int itemId, int index, MenuItem menuItem);
}
