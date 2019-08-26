package com.aman.clothesshop.ui.home;

import androidx.annotation.NonNull;

import com.aman.clothesshop.model.response.Product;
import com.aman.clothesshop.services.ApiHelper;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest extends TestCase {

    private static final String TEST_ERROR = "error";

    private List<Product> productList;
    private HomePresenter presenter;

    @Mock private HomeFragment view;
    @Mock private ApiHelper apiInterface;

    @Mock private Consumer<List<Product>> successConsumer;
    @Mock private Consumer<Throwable> failConsumer;

    @Override
    @Before
    public void setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                return new TestScheduler();
            }
        });


        productList = new ArrayList<>();

        Product productOne = new Product();
        productOne.setProductId(1);
        productOne.setName("Almond Toe Court Shoes, Patent Black");
        productOne.setCategory("Women's Footwear");

        Product productTwo = new Product();
        productTwo.setProductId(10);
        productTwo.setCategory("Men's Formalwear");
        productTwo.setName("Sharkskin Waistcoat, Charcoal");

        productList.add(productOne);
        productList.add(productTwo);

        presenter = new HomePresenter(view, apiInterface);
    }

    @Test
    public void it_should_filter_list_By_Category() {
        String category = "Women's Footwear";
        List<Product> filteredList = presenter.filterByCategory(productList, category);
        Assert.assertEquals(filteredList.size(), 1);
        Assert.assertEquals(filteredList.get(0).getCategory(), category);
    }

    @Test
    public void it_should_test_onCallGetAllProducts_Success() throws Exception {

        Observable observable = Observable.just(productList);
        observable.subscribe(successConsumer, failConsumer);
        Mockito.when(this.apiInterface.getAllProducts()).thenReturn(observable);

        presenter.getAllProducts();

        verify(successConsumer, atLeastOnce()).accept(eq(productList));
        verify(failConsumer, never()).accept(any(Throwable.class));
        verify(view, times(1)).processResponse(eq(productList));
    }

    @Test
    public void is_should_test_onGetAllProducts_Failure() throws Exception {
        Throwable exception = new Throwable(TEST_ERROR);
        Observable<List<Product>> observable = Observable.error(exception);
        observable.subscribe(successConsumer, failConsumer);
        when(this.apiInterface.getAllProducts()).thenReturn(observable);

        presenter.getAllProducts();

        verify(failConsumer).accept(eq(exception));
        verify(successConsumer, never()).accept(productList);
        verify(view).processError(eq(TEST_ERROR));
    }


    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        productList = null;
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}