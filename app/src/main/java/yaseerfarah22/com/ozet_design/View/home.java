package yaseerfarah22.com.ozet_design.View;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import yaseerfarah22.com.ozet_design.Adapter.Card_cart_adapter;
import yaseerfarah22.com.ozet_design.Adapter.Card_pro_adapter;
import yaseerfarah22.com.ozet_design.Adapter.Recycleview_Cart_Adapter;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.Model.ViewModelHelper;
import yaseerfarah22.com.ozet_design.Product;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.Adapter.Slider_Adapter;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {



    FirebaseFirestore db;
    CollectionReference product_R;

    @Inject
    ViewModelFactory viewModelFactory;



    Timer timer;
    int page=0;
    ViewPager viewPager;
    LinearLayout linearLayout;
    Slider_Adapter sliderAdapter;
    TextView[] dots;
    RecyclerView recyclerView1,recyclerView2,recyclerView3;
    ImageView imageView1,imageView2,imageView3;
    TextView textView1,textView2,textView3;
    List<Card_pro_adapter> cardProAdapters=new ArrayList<>();
    Recycleview_Cart_Adapter recycleviewCartAdapter;

    List<String>titles=new ArrayList<>();
    List<Integer>icons=new ArrayList<>();
    List<String>flags=new ArrayList<>();

    UserCollectionViewModel userCollectionViewModel;


    List<Product> products;
    int[] ids1={
            R.id.cat_logo1,
            R.id.cat_title1,
            R.id.cat_recycle1
    };
    int[] ids2={
            R.id.cat_logo2,
            R.id.cat_title2,
            R.id.cat_recycle2
    };
    int[] ids3={
            R.id.cat_logo3,
            R.id.cat_title3,
            R.id.cat_recycle3
    };


    private List<String> slider_image;



    public home() {
        // Required empty public constructor
        titles.add("New");
        titles.add("New");
        titles.add("New");
        titles.add("New");
        titles.add("New");
        icons.add(R.drawable.ic_new_releases_black_24dp);
        icons.add(R.drawable.ic_new_releases_black_24dp);
        icons.add(R.drawable.ic_new_releases_black_24dp);
        icons.add(R.drawable.ic_new_releases_black_24dp);
        icons.add(R.drawable.ic_new_releases_black_24dp);
        flags.add("New");
        flags.add("New");
        flags.add("New");
        flags.add("New");
        flags.add("New");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AndroidSupportInjection.inject(this);

        slider_image=new ArrayList<>();
        slider_image.add("https://cdn.shopify.com/s/files/1/0752/2545/t/69/assets/index_marketing_module_image_1.jpg?14899848977591351276");
        slider_image.add("https://cdn.shopify.com/s/files/1/0752/2545/t/69/assets/index_marketing_module_image_3.jpg?14899848977591351276");
        slider_image.add("https://cdn.shopify.com/s/files/1/0752/2545/files/banner_longsleeve_2048x512_1_2048x2048_9a83ec92-7193-4140-8aaa-a6faf29312d5_2048x2048.jpg?v=1542047592");

        db=FirebaseFirestore.getInstance();
        product_R=db.collection("Products");

        userCollectionViewModel= ViewModelProviders.of(this,viewModelFactory).get(UserCollectionViewModel.class);
        OzetViewModel ozetViewModel= ViewModelProviders.of(this,viewModelFactory).get(OzetViewModel.class);
        recycleviewCartAdapter=new Recycleview_Cart_Adapter(this,getActivity(),getContext(),titles,flags,icons,slider_image,MainActivity.page,userCollectionViewModel,ozetViewModel);

       // Toast.makeText(getContext(),"hi",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_home, container, false);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        /*
        viewPager=(ViewPager)view.findViewById(R.id.view_pager);
        linearLayout=(LinearLayout)view.findViewById(R.id.linear);

        sliderAdapter=new Slider_Adapter(view.getContext(),slider_image,0,getResources(),getActivity());
        viewPager.setAdapter(sliderAdapter);
        Add_Dots(view.getContext(),0);
        viewPager.setCurrentItem(0);
        viewpager_timer(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page=position;
                Add_Dots(view.getContext(),position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
*/



        recyclerView1=(RecyclerView)view.findViewById(R.id.home_recycleview);
        recyclerView1.setNestedScrollingEnabled(false);

        recyclerView1.setAdapter(recycleviewCartAdapter);
        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));



        return view;
    }


   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //*** RecyclerView1 ***

        products=new ArrayList<>();
        insertP();
        ViewModelHelper viewModelHelper= (ViewModelHelper) getArguments().getSerializable("ViewModel");
        OzetViewModel ozetViewModel= ViewModelProviders.of(this,viewModelFactory).get(OzetViewModel.class);

        ozetViewModel.getProductNewLiveData().observe(this, new Observer<List<Product_info>>() {
            @Override
            public void onChanged(@Nullable List<Product_info> product_infos) {

                Recycle_build(getActivity(),imageView1,textView1,recyclerView1,ids1,product_infos,"New");

            }
        });
        data("Offers");
        data("Fire");






    }
*/
    public void Add_Dots(Context context, int i){
        dots=new TextView[sliderAdapter.getCount()];
        linearLayout.removeAllViews();
        for (int b=0;b<dots.length;b++){
            dots[b]=new TextView(context);
            dots[b].setText(Html.fromHtml("&#8226"));
            dots[b].setTextSize(35);
            dots[b].setTextColor(getResources().getColor(R.color.grey));
            linearLayout.addView(dots[b]);
        }

        dots[i].setTextColor(Color.WHITE);

    }



    private void insertP (){
        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48428806_2229516080610314_3887866858048585728_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=7f9746600354e5c422f779ef434f7c7a&oe=5CC999B7","Havasu Falls1","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49069777_2229516110610311_7011194593555251200_n.jpg?_nc_cat=100&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=8c72f56841e69f59eec6e1865aa6d15c&oe=5CB9D05D","Havasu Falls2","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48929009_2229516073943648_5233734876460482560_n.jpg?_nc_cat=110&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=5f561e649babd1fd6ddf74012b847898&oe=5CFA4042","Havasu Falls3","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49058316_2229515937276995_4088007953175543808_n.jpg?_nc_cat=107&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=333b1c94050e17d91689e4e283ecd935&oe=5CC26850","Havasu Falls4","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49124900_2229515973943658_4611059687641579520_n.jpg?_nc_cat=104&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=94a3e7500d86619e61bd8a7dbcc386a6&oe=5CBD3E79","Havasu Falls5","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49638368_2229515990610323_7627782632599715840_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=e7e7c907079f58399d68a3996a76e399&oe=5CC16763","Havasu Falls6","50.00 .EGP"));
    }


    private void Recycle_build(FragmentActivity view, ImageView imageView, TextView textView, RecyclerView recyclerView, int[] ids, List<Product_info> product_infos, String flag){

        final Card_pro_adapter cardProAdapter;

        cardProAdapter= new Card_pro_adapter(getContext(),product_infos,userCollectionViewModel,1,getActivity(),this);

        cardProAdapters.add(cardProAdapter);
       /* userCollectionViewModel.getLikeLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                cardProAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),String.valueOf(((List<Likes>) userCollectionViewModel.getLikeLiveData().getValue()).size()), Toast.LENGTH_LONG).show();
            }
        });*/

        imageView=(ImageView)view.findViewById(ids[0]);
        textView=(TextView)view.findViewById(ids[1]);
        recyclerView=(RecyclerView)view.findViewById(ids[2]);

        recyclerView.setAdapter(cardProAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
       // Toast.makeText(getContext(),String.valueOf(cardProAdapters.size()), Toast.LENGTH_LONG).show();



    }


    /// timer for viewpager///////////

    private void viewpager_timer(int second){

       // page=viewPager.getCurrentItem();
       // Toast.makeText(getActivity(),"timer",Toast.LENGTH_LONG).show();
        timer=new Timer();
        timer.schedule(new Timer_task(),5*1000,second*1000);



    }


    ////////// cancel Timer//////////////////

    public void cancel_timer(){

        recycleviewCartAdapter.cancel_timer();

    }





    //////////////////////////////// inner Class for Timer Task//////////////////////////////////////////////


    class Timer_task extends TimerTask{


        @Override
        public void run() {

            if(getActivity()!=null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(page>=2){
                            page=0;
                        }
                        else {
                            page++;
                        }

                        viewPager.setCurrentItem(page);

                    }
                });
            }



        }
    }




    ///////////////////////////////////////Load Data/////////////////////////////////

    private void data( final String flag){

         final List<Product_info> product_infos=new ArrayList<>();
         Query query=null;

        if (flag.matches("New")){
           query= product_R.orderBy("product_date", Query.Direction.DESCENDING).limit(6);


        }
        else if (flag.matches("Offers")){

            query= product_R.orderBy("offers", Query.Direction.DESCENDING).limit(6);
        }
        else if (flag.matches("Fire")){

            query= product_R.orderBy("purchase", Query.Direction.DESCENDING).limit(6);
        }



       query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
                            product_infos.add(querySnapshot.toObject(Product_info.class));

                            if(flag.matches("Offers")){
                                Recycle_build(getActivity(),imageView2,textView2,recyclerView2,ids2,product_infos,flag);

                            }else if(flag.matches("New")){
                                Recycle_build(getActivity(),imageView1,textView1,recyclerView1,ids1,product_infos,flag);

                            }else if(flag.matches("Fire")){

                                Recycle_build(getActivity(),imageView3,textView3,recyclerView1,ids3,product_infos,flag);

                            }

                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }

                });


    }





    ///////////////////////////////////
    public int getPage(){

        return recycleviewCartAdapter.getPage();
    }

}
