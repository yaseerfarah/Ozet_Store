package yaseerfarah22.com.ozet_design.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import yaseerfarah22.com.ozet_design.Adapter.Card_order_adapter;
import yaseerfarah22.com.ozet_design.Product;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;

public class Order_ extends AppCompatActivity {


    @Inject
    ViewModelFactory viewModelFactory;


    RecyclerView recyclerView;
    List<Product> products;
    Card_order_adapter card_order_adapte;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_);



        AndroidInjection.inject(this);

        toolbar=(Toolbar)findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);


        products=new ArrayList<>();
        insertP();
        card_order_adapte=new Card_order_adapter(Order_.this,products);
        recyclerView=(RecyclerView)findViewById(R.id.order_recycleview);
        recyclerView.setAdapter(card_order_adapte);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));


    }


    private void insertP () {
        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48428806_2229516080610314_3887866858048585728_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=7f9746600354e5c422f779ef434f7c7a&oe=5CC999B7", "Havasu Falls", "50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49069777_2229516110610311_7011194593555251200_n.jpg?_nc_cat=100&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=8c72f56841e69f59eec6e1865aa6d15c&oe=5CB9D05D", "Havasu Falls", "50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48929009_2229516073943648_5233734876460482560_n.jpg?_nc_cat=110&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=5f561e649babd1fd6ddf74012b847898&oe=5CFA4042", "Havasu Falls", "50.00 .EGP"));

    }

}
