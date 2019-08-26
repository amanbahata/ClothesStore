package com.aman.clothesshop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aman.clothesshop.R;
import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.services.ApiManager;
import com.aman.clothesshop.ui.detail.ProductDetailFragment;

import java.util.List;

public class HomeFragment extends Fragment {

    private final int NUMBER_OF_COLUMNS = 2;
    private final String PRODUCT_ID = "productId";
    private HomePresenter presenter;
    private RecyclerView recyclerView;
    private HomeAdaptor adaptor;
    private List<Product> productsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.rvProducts);
        presenter = new HomePresenter(this, ApiManager.getInstance());
        presenter.getAllProducts();
        return root;
    }

    void processResponse(List<Product> products) {
        productsList = products;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_OF_COLUMNS));
        adaptor = new HomeAdaptor(getContext(), productsList, new HomeAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                swapFragment(item);
            }
        });
        recyclerView.setAdapter(adaptor);
    }

    void processError(String error){
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_manu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_button:
            case R.id.category_women:
            case R.id.category_men:
                break;
            default:
                if (productsList != null && !productsList.isEmpty()) {
                    List<Product> filtered = presenter.filterByCategory(productsList, item.getTitle().toString());
                    adaptor.updateRecyclerData(filtered);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void swapFragment(Product item) {

        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, item.getProductId());

        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(bundle);
        assert getFragmentManager() != null;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}