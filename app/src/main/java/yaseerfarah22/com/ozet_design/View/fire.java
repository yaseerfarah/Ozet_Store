package yaseerfarah22.com.ozet_design.View;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import yaseerfarah22.com.ozet_design.Adapter.Card_pro_adapter;
import yaseerfarah22.com.ozet_design.Model.Array_Helper;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.Model.ViewModelHelper;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class fire extends Fragment {

    RecyclerView recyclerView;
    List<Product_info>products;
    Card_pro_adapter cardProAdapter;
    FrameLayout frameLayout;

    UserCollectionViewModel userCollectionViewModel;
    OzetViewModel ozetViewModel;

    @Inject
    ViewModelFactory viewModelFactory;



    public fire() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AndroidSupportInjection.inject(this);

        userCollectionViewModel= ViewModelProviders.of(this,viewModelFactory).get(UserCollectionViewModel.class);
        ozetViewModel= ViewModelProviders.of(this,viewModelFactory).get(OzetViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View view=inflater.inflate(R.layout.fragment_fire, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);

        String flag=  getArguments().getString("Flag");
        frameLayout=(FrameLayout)view.findViewById(R.id.fire_content);
        if(flag.matches("Holder")){

            // Calculate ActionBar's height
            TypedValue tv = new TypedValue();
            int actionBarHeight=0;
            if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
            FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            //Toast.makeText(view.getContext(),String.valueOf(getResources().getDimensionPixelSize(R.attr.actionBarSize)),Toast.LENGTH_LONG).show();

            layoutParams.setMargins(0,actionBarHeight,0,0);
            frameLayout.setLayoutParams(layoutParams);

        }

        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Array_Helper arrayHelper= (Array_Helper) getArguments().getSerializable("Products_list");
        //products=arrayHelper.getArray();
        //ViewModelHelper viewModelHelper= (ViewModelHelper) getArguments().getSerializable("ViewModel");
       // OzetViewModel ozetViewModel=viewModelHelper.getOzetViewModel();



        products=(List<Product_info>)ozetViewModel.getProductLiveData().getValue();
        List<Likes> likesList=(List<Likes>)userCollectionViewModel.getLikeLiveData().getValue();
        cardProAdapter=new Card_pro_adapter(view.getContext(),products,userCollectionViewModel,0, getActivity(),this);
        recyclerView.setAdapter(cardProAdapter);


        ozetViewModel.getProductLiveData().observe(this, new Observer<List<Product_info>>() {
                    @Override
                    public void onChanged(@Nullable List<Product_info> product_infos) {

                        cardProAdapter.notifyDataSetChanged();
                    }
                }

        );

        userCollectionViewModel.getLikeLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                cardProAdapter.notifyDataSetChanged();
            }
        });






        return view;
    }



  /*  private void insertP (){
        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48428806_2229516080610314_3887866858048585728_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=7f9746600354e5c422f779ef434f7c7a&oe=5CC999B7","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49069777_2229516110610311_7011194593555251200_n.jpg?_nc_cat=100&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=8c72f56841e69f59eec6e1865aa6d15c&oe=5CB9D05D","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48929009_2229516073943648_5233734876460482560_n.jpg?_nc_cat=110&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=5f561e649babd1fd6ddf74012b847898&oe=5CFA4042","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49058316_2229515937276995_4088007953175543808_n.jpg?_nc_cat=107&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=333b1c94050e17d91689e4e283ecd935&oe=5CC26850","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49124900_2229515973943658_4611059687641579520_n.jpg?_nc_cat=104&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=94a3e7500d86619e61bd8a7dbcc386a6&oe=5CBD3E79","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49638368_2229515990610323_7627782632599715840_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=e7e7c907079f58399d68a3996a76e399&oe=5CC16763","Havasu Falls","50.00 .EGP"));
    }
*/

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {

                   // outRect.left = spacing; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = spacing;//(column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }



    public void filter(String order){

        if(order.matches("Pricehigher")){
            Collections.sort(products,  Collections.reverseOrder(new Comparator<Product_info>() {
                @Override
                public int compare(Product_info product_info, Product_info t1) {
                    return Double.compare(Double.valueOf(product_info.getPrice()),Double.valueOf(t1.getPrice()));
                }

            }));



            recyclerView.setAdapter(cardProAdapter);
        }


        if(order.matches("Pricelow")){
            Collections.sort(products,  new Comparator<Product_info>() {
                @Override
                public int compare(Product_info product_info, Product_info t1) {
                    return Double.compare(Double.valueOf(product_info.getPrice()),Double.valueOf(t1.getPrice()));
                }


            });



            recyclerView.setAdapter(cardProAdapter);        }


    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }





}
