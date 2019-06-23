package yaseerfarah22.com.ozet_design.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import yaseerfarah22.com.ozet_design.Adapter.Card_cart_adapter;
import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Product;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;


public class Cart extends Fragment {

    RecyclerView recyclerView;
    List<Product> products;
    TextView total;
    Button confirm;
    Card_cart_adapter cardcartadapter;
    String flag;
    float price=0f;

    @Inject
    ViewModelFactory viewModelFactory;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        AndroidSupportInjection.inject(this);

        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        products=new ArrayList<>();
        insertP();
        flag=getArguments().getString("Flag");
        total=(TextView)view.findViewById(R.id.total_price);

        recyclerView=(RecyclerView)view.findViewById(R.id.card_recycleview);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));



        UserCollectionViewModel userCollectionViewModel= ViewModelProviders.of(this,viewModelFactory).get(UserCollectionViewModel.class);


       final List<Cart_info>[]cart_infoLists=new List[1];
        cart_infoLists[0]=new ArrayList<>();

        cardcartadapter=new Card_cart_adapter(getContext(),cart_infoLists[0],flag,getActivity());
        recyclerView.setAdapter(cardcartadapter);

        userCollectionViewModel.getCartLiveData().observe(this, new Observer< List<Cart_info>>() {
            @Override
            public void onChanged(@Nullable  List<Cart_info> cart_infos) {
                cart_infoLists[0].addAll(cart_infos);
                cardcartadapter.notifyDataSetChanged();

            }
        });

        confirm=(Button)view.findViewById(R.id.card_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),CheckOut.class));
            }
        });

        count_price();



        return view;
    }

    public void count_price(){
        price=0;
        for (int i = 0; i< MainActivity.userCart.size(); i++){
            price+=Float.valueOf(MainActivity.userCart.get(i).getPro_price())*Float.valueOf(MainActivity.userCart.get(i).getQuantity());
        }
        total.setText(String.valueOf(price)+" "+".EGP");
    }



    private void insertP () {
        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48428806_2229516080610314_3887866858048585728_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=7f9746600354e5c422f779ef434f7c7a&oe=5CC999B7", "Havasu Falls", "50.00"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49069777_2229516110610311_7011194593555251200_n.jpg?_nc_cat=100&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=8c72f56841e69f59eec6e1865aa6d15c&oe=5CB9D05D", "Havasu Falls", "50.00"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48929009_2229516073943648_5233734876460482560_n.jpg?_nc_cat=110&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=5f561e649babd1fd6ddf74012b847898&oe=5CFA4042", "Havasu Falls", "50.00"));

    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
