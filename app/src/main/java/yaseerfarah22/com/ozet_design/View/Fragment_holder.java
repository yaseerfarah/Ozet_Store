package yaseerfarah22.com.ozet_design.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.transition.Explode;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import yaseerfarah22.com.ozet_design.DatabaseMethod;
import yaseerfarah22.com.ozet_design.Model.Array_Helper;
import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Order_info;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.Product;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;

public class Fragment_holder extends AppCompatActivity implements Product_desc.OnFragmentInteractionListener,DatabaseMethod ,HasSupportFragmentInjector {

    FrameLayout frameLayout,fragment;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    TextView title;
    List<Product> products;
    MenuItem rank;
    MenuItem price_higher;
    MenuItem price_low;
    String category;
    RelativeLayout relativeLayout;
    private float toolheight;
    TextView cart_badge_text;
    RelativeLayout cart_badge;
    private static final long MOVE_DEFAULT_TIME = 300;
    private static final long FADE_DEFAULT_TIME = 200;


    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private FirebaseFirestore db;
    private CollectionReference user_R;
    private CollectionReference product_R;
    private CollectionReference likes_R;
    private CollectionReference order_R;
    private CollectionReference cart_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);


        AndroidInjection.inject(this);


        db=FirebaseFirestore.getInstance();
        product_R=db.collection("Products");
        likes_R=db.collection("Likes");
        order_R=db.collection("Order");
        cart_R=db.collection("Cart");

        relativeLayout=(RelativeLayout)findViewById(R.id.bar_container);
        products=new ArrayList<>();
        appBarLayout=(AppBarLayout)findViewById(R.id.holder_bar);
        toolheight=appBarLayout.getHeight();
        toolbar=(Toolbar)findViewById(R.id.holder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);


        title=(TextView)findViewById(R.id.holder_title);
        frameLayout=(FrameLayout)findViewById(R.id.fragment_holder);



        title.setText(getIntent().getStringExtra("category"));


        category=getIntent().getStringExtra("category");

        data(category);


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        rank=menu.findItem(R.id.shopping_filter);
        price_higher=menu.findItem(R.id.price_higher_low);
        price_low=menu.findItem(R.id.price_low_higher);
        MenuItem item=menu.findItem(R.id.shopping_bag);

        cart_badge=(RelativeLayout)item.getActionView();

        cart_badge_text=(TextView)cart_badge.findViewById(R.id.badge_text);
        UserCollectionViewModel userCollectionViewModel= ViewModelProviders.of(this,viewModelFactory).get(UserCollectionViewModel.class);

        userCollectionViewModel.getCartLiveData().observe(this, new Observer< List<Cart_info>>() {
            @Override
            public void onChanged(@Nullable  List<Cart_info> cart_infos) {

                if(cart_infos.size()==0){
                    dismiss_badge();
                }else {
                    show_badge(cart_infos.size());
                }

            }
        });

        ImageButton bag_icon=(ImageButton) cart_badge.findViewById(R.id.shopping_bag_image);

        bag_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSupportFragmentManager().findFragmentByTag("Cart")==null) {

                    Cart_fragment();
                }
            }
        });



        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag("Cart")!=null&&getSupportFragmentManager().findFragmentByTag("Cart").isVisible()){

            // Toast.makeText(this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_LONG).show();

           /* if(MainActivity.userCart.size()==0){
                dismiss_badge();
            }else {
                show_badge();
            }*/

            if (getSupportFragmentManager().getBackStackEntryCount()==1){
                title.setText("Ozet");
                rank.setVisible(true);
            }
            else {

                slideToBottom(relativeLayout);
            }

            super.onBackPressed();



        }
        else if(getSupportFragmentManager().findFragmentByTag("Product")!=null&&getSupportFragmentManager().findFragmentByTag("Product").isVisible()){

            slideToTop(relativeLayout);
            super.onBackPressed();

        }


        else {
            super.onBackPressed();
        }
    }






    private void set_fragment(List products){

        fire fire_=new fire();
        Bundle bundle=new Bundle();
        Array_Helper arrayHelper=new Array_Helper(products);
        bundle.putSerializable("Products_list",arrayHelper);
        bundle.putString("Flag","Holder");
        fire_.setArguments(bundle);
        fire_.setAllowEnterTransitionOverlap(false);
        fire_.setEnterTransition(new Fade());
        fire_.setExitTransition(new Explode().setDuration(300));
        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,fire_,"Fragment");
        fragmentTransaction.commit();
    }




    private void insertP (){
        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48428806_2229516080610314_3887866858048585728_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=7f9746600354e5c422f779ef434f7c7a&oe=5CC999B7","Havasu Falls","50.00"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49069777_2229516110610311_7011194593555251200_n.jpg?_nc_cat=100&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=8c72f56841e69f59eec6e1865aa6d15c&oe=5CB9D05D","Havasu Falls","41.00"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48929009_2229516073943648_5233734876460482560_n.jpg?_nc_cat=110&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=5f561e649babd1fd6ddf74012b847898&oe=5CFA4042","Havasu Falls","40.99"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49058316_2229515937276995_4088007953175543808_n.jpg?_nc_cat=107&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=333b1c94050e17d91689e4e283ecd935&oe=5CC26850","Havasu Falls","500.00"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49124900_2229515973943658_4611059687641579520_n.jpg?_nc_cat=104&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=94a3e7500d86619e61bd8a7dbcc386a6&oe=5CBD3E79","Havasu Falls","60.00"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49638368_2229515990610323_7627782632599715840_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=e7e7c907079f58399d68a3996a76e399&oe=5CC16763","Havasu Falls","70.00"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onFragmentInteraction(Product_info product, View imageView) {
        Product_desc productDesc=new Product_desc();
        Bundle arg=new Bundle();
        arg.putSerializable("key", (Serializable) product);
        arg.putSerializable("Image",  new Array_Helper(imageView));
        arg.putString("Flag","Holder");
        productDesc.setArguments(arg);

        TransitionSet enterTransitionSet = new TransitionSet();
        enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
        enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
        enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
        productDesc.setSharedElementEnterTransition(enterTransitionSet);

        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addSharedElement(imageView,imageView.getTransitionName());
        fragmentTransaction.replace(R.id.fragment_holder,productDesc,"Product");
        slideToBottom(relativeLayout);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.hm,menu);




        return super.onCreateOptionsMenu(menu);
    }







    /////////////////////////////////////////////////////////

    public void show_badge(int size){

        cart_badge_text.setVisibility(View.VISIBLE);
        cart_badge_text.setText(String.valueOf(size));
    }

    ///////////////////////////////////////////////////////////


    public void dismiss_badge(){

        cart_badge_text.setVisibility(View.GONE);
        cart_badge_text.setText(String.valueOf(MainActivity.userCart.size()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if(item.getItemId()==R.id.shopping_bag){

            if(getSupportFragmentManager().findFragmentByTag("Cart")==null) {

               Cart_fragment();
            }

            return true;

        }


        if(item.getItemId()==R.id.price_higher_low){

            filter("Pricehigher");
            if (price_low.isChecked()){
                price_low.setChecked(false);
            }
            price_higher.setChecked(true);

            return true;
        }

        if(item.getItemId()==R.id.price_low_higher){

            filter("Pricelow");
            if (price_higher.isChecked()){
                price_higher.setChecked(false);
            }
            price_low.setChecked(true);

            return true;
        }

        return false;
    }




    private void filter(String order){

        fire fragment=(fire) getSupportFragmentManager().findFragmentByTag("Fragment");

        fragment.filter(order);

    }

    //// Bottomnavigation animation///////////////



    ///slide down

    public void slideToBottom(View view) {
        TranslateAnimation animate;
            animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(380);
        // animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }

    /// slide up

    public void slideToTop(View view){
        TranslateAnimation animate;
            animate = new TranslateAnimation(0,0,-view.getHeight(),toolheight);
        animate.setDuration(380);
        //animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }



    /// open cart fragment ////////

    public void Cart_fragment(){
        Cart cart = new Cart();
        Bundle bundle=new Bundle();
        bundle.putString("Flag","Holder");
        cart.setArguments(bundle);
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, cart, "Cart");
        fragmentTransaction.addToBackStack(null);

        rank.setVisible(false);
        if(relativeLayout.getVisibility()==View.INVISIBLE){
            slideToTop(relativeLayout);
        }
        fragmentTransaction.commit();
        title.setText("Cart");

    }


    ///////////////////////////////////////////////count cart price ////////////////////////////////////////////////////////

    public void count_price(){

        if(getSupportFragmentManager().findFragmentByTag("Cart")!=null&&getSupportFragmentManager().findFragmentByTag("Cart").isVisible()){

            Cart cart=(Cart)getSupportFragmentManager().findFragmentByTag("Cart");
            cart.count_price();
        }
    }



    /////////////////////////////////////////Load data////////////////////////////////////////////////

    private void data(String flag){

        final List<Product_info> product_infos=new ArrayList<>();
        Query query;


            query= product_R.whereEqualTo("category",flag);









        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
                            product_infos.add(querySnapshot.toObject(Product_info.class));
                            set_fragment(product_infos);

                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }

        });



    }


    /////////////////////////////////////////////////////// Add to Like collection/////////////////////////////////

    @Override
    public void add_like(Product_info product_info){

        Likes likes=new Likes(likes_R.document().getId(), MainActivity.userInfo.getUser_id().trim(),product_info);

        MainActivity.userLikes.add(likes);
        likes_R.document(likes.getLikeId()).set(likes);



    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void retrieve_likes(){
        likes_R.whereEqualTo("userId", MainActivity.userInfo.getUser_id()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){

                    MainActivity.userLikes.add(documentSnapshot.toObject(Likes.class));
                }
            }
        });

    }


    //////////////////////////////////////////////////// Delete Like///////////////////////////////////////////////////


    @Override
    public void delete_like(Likes likes){

        for (int i = 0; i< MainActivity.userLikes.size(); i++){

            if(likes.getLikeId().matches(MainActivity.userLikes.get(i).getLikeId())){
                MainActivity.userLikes.remove(i);
                break;
            }
        }


        likes_R.document(likes.getLikeId()).delete();

    }


    ///////////////////////////////////////////////////////Add to user Cart///////////////////////////////////////

    @Override
    public void add_to_cart(String size,String quantity ,Product_info product_info){

        Cart_info cart_info=new Cart_info(cart_R.document().getId(), MainActivity.userInfo.getUser_id(),product_info.getId(),size,quantity,product_info.getId(),product_info.getName(),product_info.getCategory(),product_info.getPrice(),product_info.getImages_url().get(0));

        MainActivity.userCart.add(cart_info);
        //show_badge();
        cart_R.document(cart_info.getCartId()).set(cart_info);

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void retrieve_carts(){

        cart_R.whereEqualTo("userId", MainActivity.userInfo.getUser_id()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot querySnapshot: queryDocumentSnapshots){

                    MainActivity.userCart.add(querySnapshot.toObject(Cart_info.class));
                }
            }
        });
    }


    //////////////////////////////////////////////////// Delete Cart///////////////////////////////////////////////////


    @Override
    public void delete_cart(Cart_info cart_info){

        for (int i = 0; i< MainActivity.userCart.size(); i++){

            if(cart_info.getCartId().matches(MainActivity.userCart.get(i).getCartId())){
                MainActivity.userCart.remove(i);
                break;
            }
        }

        /*if(MainActivity.userCart.size()==0){
            dismiss_badge();
        }else {
            show_badge();
        }*/

        cart_R.document(cart_info.getCartId()).delete();

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void delete_AllCarts(){

        WriteBatch batch=db.batch();
        for (int i = 0; i< MainActivity.userCart.size(); i++){
            DocumentReference documentReference=cart_R.document(MainActivity.userCart.get(i).getCartId());
            batch.delete(documentReference);
        }

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                MainActivity.userCart.clear();
            }
        });




    }

    ////////////////////////////////////////////////// Add order////////////////////////////////////////////////////////////////


    @Override
    public  void add_order(final List<Cart_info> cartInfoList, final String order_st, final String order_md, final String order_dt){

        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                for (int i=0;i<cartInfoList.size();i++){
                    DocumentReference documentReference=product_R.document(cartInfoList.get(i).getPro_id());
                    DocumentSnapshot documentSnapshot=transaction.get(documentReference);
                    Product_info product_info=documentSnapshot.toObject(Product_info.class);
                    int pur=Integer.valueOf(product_info.getPurchase())+1;
                    int stoke=Integer.valueOf(product_info.getStock())-1;
                    transaction.update(documentReference,"purchase",pur);
                    transaction.update(documentReference,"stock",stoke);
                    Order_info order_info=new Order_info(MainActivity.userInfo.getUser_id(),order_R.document().getId(),order_st,order_md,cartInfoList.get(i),order_dt, MainActivity.userInfo);
                    transaction.set(order_R.document(order_info.getOrder_id()),order_info);


                }



                return null;
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void retrieve_order(){

        order_R.whereEqualTo("user_id", MainActivity.userInfo.getUser_id()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){

                    MainActivity.orderInfos.add(querySnapshot.toObject(Order_info.class));
                }
            }
        });
    }


    //////////////////////////////////////////////////// Delete order///////////////////////////////////////////////////


    @Override
    public void delete_order(Order_info order_info){

        for (int i = 0; i< MainActivity.orderInfos.size(); i++){

            if(order_info.getOrder_id().matches(MainActivity.orderInfos.get(i).getOrder_id())){
                MainActivity.orderInfos.remove(i);
                break;
            }
        }


        order_R.document(order_info.getOrder_id()).delete();

    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
