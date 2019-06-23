package yaseerfarah22.com.ozet_design.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.transition.Explode;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import yaseerfarah22.com.ozet_design.Adapter.Card_cart_adapter;
import yaseerfarah22.com.ozet_design.Dagger2.Module.Component.AppComponent;
import yaseerfarah22.com.ozet_design.Dagger2.Module.ViewModelModule;
import yaseerfarah22.com.ozet_design.DatabaseMethod;
import yaseerfarah22.com.ozet_design.Model.Array_Helper;
import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Order_info;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.Model.User_info;
import yaseerfarah22.com.ozet_design.Model.ViewModelHelper;
import yaseerfarah22.com.ozet_design.Product;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;

public class MainActivity extends AppCompatActivity implements Product_desc.OnFragmentInteractionListener, DatabaseMethod,HasSupportFragmentInjector {


    private static final long MOVE_DEFAULT_TIME = 300;
    private static final long FADE_DEFAULT_TIME = 200;
    public static User_info userInfo;
    public static List<Likes> userLikes;
    public static List<Cart_info> userCart;
    public static List<Order_info> orderInfos;
   /* public static ArrayList<Product_info> cLothes;
    public static ArrayList<Product_info> sHoes;
    public static ArrayList<Product_info> pAnts;
    public static ArrayList<Product_info> fIre;*/




    private  String TAG="Hash Key";

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference user_R;
    private CollectionReference product_R;
    private CollectionReference likes_R;
    private CollectionReference order_R;
    private CollectionReference cart_R;
    private OzetViewModel ozetViewModel;
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    AppComponent appComponent;
    UserCollectionViewModel userCollectionViewModel;


    Observer<List<Cart_info>> cartObserver=new Observer< List<Cart_info>>() {
        @Override
        public void onChanged(@Nullable  List<Cart_info> cart_infos) {

            if(cart_infos.size()==0){
                dismiss_badge();
            }else {
                show_badge(cart_infos.size());
            }

        }
    };



    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    BottomNavigationView bottomNavigationView;
    ImageView header_image;
    FrameLayout frameLayout,fragment;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    //ImageButton cart_icon;
    TextView title,header_name;
    private float bottomheight;
    private float toolheight;
    public static int page=0;
    List<Product> products;

    TextView cart_badge_text;
    RelativeLayout cart_badge;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.t);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // printHashKey(this);


        AndroidInjection.inject(this);

         userCollectionViewModel= ViewModelProviders.of(this,viewModelFactory).get(UserCollectionViewModel.class);

        userCollectionViewModel.getCartLiveData().observe(this,cartObserver);

        ozetViewModel= ViewModelProviders.of(this,viewModelFactory).get(OzetViewModel.class);
        firebaseFirestore=FirebaseFirestore.getInstance();
        user_R=firebaseFirestore.collection("User");
        product_R=firebaseFirestore.collection("Products");
        likes_R=firebaseFirestore.collection("Likes");
        order_R=firebaseFirestore.collection("Order");
        cart_R=firebaseFirestore.collection("Cart");

        userLikes=new ArrayList<>();
        userCart=new ArrayList<>();


        products=new ArrayList<>();
        appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // Toast.makeText(this,String.valueOf(toolbar.getElevation()),Toast.LENGTH_LONG).show();

        toolheight=appBarLayout.getHeight();
        title=(TextView)findViewById(R.id.app_title);
      //  cart_icon=(ImageButton)findViewById(R.id.cart_icon);
        frameLayout=(FrameLayout)findViewById(R.id.frame);
        fragment=(FrameLayout)findViewById(R.id.fragment_frame);
         drawerLayout =(DrawerLayout)findViewById(R.id.container);
         toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();






        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
       // Toast.makeText(this,String.valueOf(bottomNavigationView.getElevation()),Toast.LENGTH_LONG).show();
        bottomheight=bottomNavigationView.getHeight();

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        bottom_icon_size(menuView);

            final NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_drawer);

            header_image=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.header_image);
            header_name=(TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_name);

            ///
            if(getIntent().getExtras().getString("Flag").matches("Google")){

                GoogleSignInAccount googleSignInAccount =(GoogleSignInAccount)getIntent().getExtras().getParcelable("Google_L");
                get_userInfo(googleSignInAccount.getEmail().toString());
                header_name.setText(googleSignInAccount.getDisplayName());
                Glide.with(getApplicationContext()).load(googleSignInAccount.getPhotoUrl()).into(header_image);

            }
            else if(getIntent().getExtras().getString("Flag").matches("Facebook")){

                AccessToken accessToken=(AccessToken)getIntent().getExtras().getParcelable("Facebook_L");
                useLoginInformation(accessToken);

            }



            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if(item.getItemId()==R.id.nav_camera){
                        startActivity(new Intent(MainActivity.this,Personal_page.class));
                        return true;
                    }
                    if (item.getItemId()==R.id.nav_gallery){
                        startActivity(new Intent(MainActivity.this,Order_.class));
                        return true;
                    }

                    if (item.getItemId()==R.id.nav_send){
                        startActivity(new Intent(MainActivity.this,Add_post.class));
                        return true;
                    }


                    if (item.getItemId()==R.id.nav_dashboard){
                        startActivity(new Intent(MainActivity.this,Admin_Dashboard.class));
                        return true;
                    }

                    if (item.getItemId()==R.id.logout){
                        if(getIntent().getExtras().getString("Flag").matches("Facebook")){
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(MainActivity.this,Login.class));
                            finish();
                            return true;

                        }

                        else if(getIntent().getExtras().getString("Flag").matches("Google")){
                            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestEmail()
                                    .build();

                            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

                            googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(MainActivity.this,Login.class));

                                }
                            });
                            finish();
                            return true;

                        }

                    }

                    return false;
                }
            });










        final home home_=new home();
        final catogery catogery_=new catogery();
        final fire fire_=new fire();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.home){
                    Fade_fragment_transition(home_);
                    set_fragment(home_,"Home");
                    return true;
                }else if(id==R.id.fire){
                    is_Home();
                    assignPage();
                    Fade_fragment_transition(fire_);
                    //data_fire(fire_);
                    set_fire(new ArrayList(),fire_);
                    return true;
                }else if(id==R.id.catogery){
                    is_Home();
                    assignPage();
                    Fade_fragment_transition(catogery_);
                    set_fragment(catogery_,"Category");
                    return true;}
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void set_fragment(android.support.v4.app.Fragment fragment,String tag){

        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //fragment.setExitTransition(new Fade().setDuration(200));
        //fragment.setEnterTransition(new Fade().setDuration(200));
        Bundle bundle=new Bundle();
        bundle.putSerializable("ViewModel",new ViewModelHelper(ozetViewModel));
        fragment.setArguments(bundle);
        fragment.setAllowEnterTransitionOverlap(false);
        fragmentTransaction.replace(R.id.frame,fragment,tag);
        fragmentTransaction.commit();
    }






    private void assignPage(){

        if(getSupportFragmentManager().findFragmentByTag("Home")!=null){
            home home=(home) getSupportFragmentManager().findFragmentByTag("Home");
            page=home.getPage();

        }
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.container);
       /* if(userCart.size()==0){
            dismiss_badge();
        }else {
            show_badge();
        }*/
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else if(getSupportFragmentManager().findFragmentByTag("Cart")!=null&&getSupportFragmentManager().findFragmentByTag("Cart").isVisible()){

            // Toast.makeText(this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_LONG).show();

            if (getSupportFragmentManager().getBackStackEntryCount()==1){
                slideToTop(bottomNavigationView,0);
                title.setText("Ozet");
            }
            else {
                slideToBottom(appBarLayout,1);
            }

            super.onBackPressed();



        }else if(getSupportFragmentManager().findFragmentByTag("Product")!=null&&getSupportFragmentManager().findFragmentByTag("Product").isVisible()){
            slideToTop(bottomNavigationView,0);
            slideToTop(appBarLayout,1);
            super.onBackPressed();

        }

        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.m,menu);





        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item=menu.findItem(R.id.shopping_bag);

        cart_badge=(RelativeLayout)item.getActionView();

        cart_badge_text=(TextView)cart_badge.findViewById(R.id.badge_text);
        dismiss_badge();


        /*userCollectionViewModel.getCartLiveData().observe(this, new Observer< List<Cart_info>>() {
            @Override
            public void onChanged(@Nullable  List<Cart_info> cart_infos) {

                if(cart_infos.size()==0){
                    dismiss_badge();
                }else {
                    show_badge(cart_infos.size());
                }

            }
        });*/






        ImageButton bag_icon=(ImageButton) cart_badge.findViewById(R.id.shopping_bag_image);

        bag_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSupportFragmentManager().findFragmentByTag("Cart")==null) {

                    is_Home();
                    Cart_fragment();
                }
            }
        });


        return super.onPrepareOptionsMenu(menu);
    }


    /////////////////////////////////////////////////////////

    public void show_badge(int size){

        cart_badge_text.setVisibility(View.VISIBLE);
        cart_badge_text.setText(String.valueOf(size));
    }

    ///////////////////////////////////////////////////////////


    public void dismiss_badge(){

        cart_badge_text.setVisibility(View.GONE);
        cart_badge_text.setText(String.valueOf(userCart.size()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.shopping_bag){

            if(getSupportFragmentManager().findFragmentByTag("Cart")==null) {

                is_Home();
                Cart_fragment();
            }

            return true;

        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onFragmentInteraction(Product_info product, View imageView) {
        final Product_desc productDesc=new Product_desc();
        Bundle arg=new Bundle();
        arg.putSerializable("key", (Serializable) product);
        arg.putSerializable("Image",  new Array_Helper(imageView));
        arg.putString("Flag","Main");
        productDesc.setArguments(arg);

        TransitionSet enterTransitionSet = new TransitionSet();
        enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
        enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
        enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
        productDesc.setSharedElementEnterTransition(enterTransitionSet);
       // productDesc.setSharedElementReturnTransition(new Fade());
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Fade enterFade = new Fade();
        enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
        enterFade.setDuration(FADE_DEFAULT_TIME);

       // productDesc.setEnterTransition(enterFade);


       // productDesc.setAllowEnterTransitionOverlap(false);
        View view=ButterKnife.findById(this,R.id.fav_button);

        Explode_fragment_transition();

        fragmentTransaction.addSharedElement(imageView,imageView.getTransitionName());
       // fragmentTransaction.addSharedElement(view,view.getTransitionName());
        //Toast.makeText(this,imageView.getTransitionName(),Toast.LENGTH_SHORT).show();
        fragmentTransaction.replace(R.id.frame,productDesc,"Product");

        slideToBottom(bottomNavigationView,0);
        slideToBottom(appBarLayout,1);

       // bottomNavigationView.setVisibility(View.INVISIBLE);
        //frameLayout.setVisibility(View.INVISIBLE);

       // appBarLayout.setAlpha(0);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    private void set_fire(List products,fire fire_){


        Bundle bundle=new Bundle();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Array_Helper arrayHelper=new Array_Helper(products);
        bundle.putSerializable("Products_list",arrayHelper);
        bundle.putSerializable("ViewModel",new ViewModelHelper(ozetViewModel));
        bundle.putString("Flag","Main");
        fire_.setArguments(bundle);
        fire_.setAllowEnterTransitionOverlap(false);
        //fire_.setEnterTransition(new Fade());
        //33fire_.setExitTransition(new Explode().setDuration(300));
        fragmentTransaction.replace(R.id.frame,fire_,"Fire");
        fragmentTransaction.commit();
    }



    private void insertP (){
        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48428806_2229516080610314_3887866858048585728_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=7f9746600354e5c422f779ef434f7c7a&oe=5CC999B7","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49069777_2229516110610311_7011194593555251200_n.jpg?_nc_cat=100&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=8c72f56841e69f59eec6e1865aa6d15c&oe=5CB9D05D","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/48929009_2229516073943648_5233734876460482560_n.jpg?_nc_cat=110&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=5f561e649babd1fd6ddf74012b847898&oe=5CFA4042","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49058316_2229515937276995_4088007953175543808_n.jpg?_nc_cat=107&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=333b1c94050e17d91689e4e283ecd935&oe=5CC26850","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49124900_2229515973943658_4611059687641579520_n.jpg?_nc_cat=104&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=94a3e7500d86619e61bd8a7dbcc386a6&oe=5CBD3E79","Havasu Falls","50.00 .EGP"));

        products.add(new Product("https://scontent-hbe1-1.xx.fbcdn.net/v/t1.0-9/fr/cp0/e15/q65/49638368_2229515990610323_7627782632599715840_n.jpg?_nc_cat=108&efg=eyJpIjoidCJ9&_nc_ht=scontent-hbe1-1.xx&oh=e7e7c907079f58399d68a3996a76e399&oe=5CC16763","Havasu Falls","50.00 .EGP"));
    }



    private void insertP2(JSONArray jsonArray){

        for (int i=0;i<jsonArray.length();i++){

            Product product=new Product();

            try {
                product.setName(jsonArray.getJSONObject(i).getString("title"));
                product.setPro_image(jsonArray.getJSONObject(i).getString("image_url"));
                product.setPrice("50.00.EGP");
                products.add(product);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    public void start_zctivity(String category){

        startActivity(new Intent(MainActivity.this,Fragment_holder.class).putExtra("category",category));

    }



    //// Bottomnavigation animation///////////////



    ///slide down

    public void slideToBottom(View view,int v) {
        TranslateAnimation animate;
        if(v==0){
            animate =new TranslateAnimation(0, 0, 0, view.getHeight());
        }
        else {
            animate = new TranslateAnimation(0,0,0,-view.getHeight());

        }
        animate.setDuration(380);
       // animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }

    /// slide up

    public void slideToTop(View view,int v){
        TranslateAnimation animate;
        if(v==0){
            animate = new TranslateAnimation(0,0,view.getHeight(),bottomheight);
        }
        else {
             animate = new TranslateAnimation(0,0,-view.getHeight(),toolheight);
        }
        animate.setDuration(380);
        //animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    //// open drawer/////
    public void open_drawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    /// open cart fragment ////////


    public void Cart_fragment(){
        Cart cart = new Cart();
        Bundle bundle=new Bundle();
        bundle.putString("Flag","Main");
        cart.setArguments(bundle);
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, cart, "Cart");
        fragmentTransaction.addToBackStack(null);

        if(appBarLayout.getVisibility()==View.INVISIBLE){
            slideToTop(appBarLayout,1);
        }
        else {
            slideToBottom(bottomNavigationView,0);
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


    //// no transition///////

    private void Fade_fragment_transition(android.support.v4.app.Fragment fragment){

        fragment.setEnterTransition(null);
        if(getSupportFragmentManager().findFragmentByTag("Fire")!=null){
            getSupportFragmentManager().findFragmentByTag("Fire").setExitTransition(null);
        }

        if(getSupportFragmentManager().findFragmentByTag("Home")!=null){
            getSupportFragmentManager().findFragmentByTag("Home").setExitTransition(null);
        }

        if(getSupportFragmentManager().findFragmentByTag("Category")!=null){
            getSupportFragmentManager().findFragmentByTag("Category").setExitTransition(null);
        }

    }

    //// yes transition///////

    private void Explode_fragment_transition(){
        Explode explode=new Explode();
        explode.setDuration(300);
        if(getSupportFragmentManager().findFragmentByTag("Fire")!=null){
            getSupportFragmentManager().findFragmentByTag("Fire").setExitTransition(explode);
        }

        if(getSupportFragmentManager().findFragmentByTag("Home")!=null){
            getSupportFragmentManager().findFragmentByTag("Home").setExitTransition(explode);
        }


    }



    /////////////// check if the current ftragment is home ////////////////////////////

    public void is_Home(){
        if(getSupportFragmentManager().findFragmentByTag("Home")!=null){
            home home=(home) getSupportFragmentManager().findFragmentByTag("Home");
            home.cancel_timer();
        }


    }


    ////////////////////////////////////
    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }



    // facebook info

    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    get_userInfo(email);
                    header_name.setText(name);

                    Glide.with(getApplicationContext()).load(image).into(header_image);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Glide.with(getApplicationContext()).pauseAllRequests();
    }



    /////////////////////////////////////////Load data////////////////////////////////////////////////

    private void data_fire(final fire fire_){

        final List<Product_info> product_infos=new ArrayList<>();
        Query query;



            query= product_R;




        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
                            product_infos.add(querySnapshot.toObject(Product_info.class));
                            set_fire(product_infos,fire_);

                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
            }

        });



    }



    //////////////////////////////////////////////////////////// bottom navigation icon size///////////////////////////////////////

    private void bottom_icon_size(BottomNavigationMenuView menuView){

        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
    }

/////////////////////////////////////////////////////// Add to Like collection/////////////////////////////////

    @Override
    public void add_like(Product_info product_info){

        Likes likes=new Likes(product_info.getId(),userInfo.getUser_id().trim(),product_info);

        userLikes.add(likes);
        likes_R.document(likes.getLikeId()).set(likes);



    }

    ////////////////////////////////////////////// Retrieve Likes////////////////////////////////////////////////////

    @Override
    public void retrieve_likes(){
        likes_R.whereEqualTo("userId",userInfo.getUser_id()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){

                    userLikes.add(documentSnapshot.toObject(Likes.class));
                }
            }
        });

    }


    //////////////////////////////////////////////////// Delete Like///////////////////////////////////////////////////


    @Override
    public void delete_like(Likes likes){

        for (int i=0;i<userLikes.size();i++){

            if(likes.getLikeId().matches(userLikes.get(i).getLikeId())){
                userLikes.remove(i);
                break;
            }
        }


        likes_R.document(likes.getLikeId()).delete();

    }


    ///////////////////////////////////////////////////////Add to user Cart///////////////////////////////////////

    @Override
    public void add_to_cart(String size,String quantity ,Product_info product_info){

        Cart_info cart_info=new Cart_info(cart_R.document().getId(),userInfo.getUser_id(),product_info.getId(),size,quantity,product_info.getId(),product_info.getName(),product_info.getCategory(),product_info.getPrice(),product_info.getImages_url().get(0));

        userCart.add(cart_info);
       // show_badge();
        cart_R.document(cart_info.getCartId()).set(cart_info);

    }


    ////////////////////////////////////////////////////////Retrieve carts///////////////////////////////////////////////////


    @Override
    public void retrieve_carts(){

        cart_R.whereEqualTo("userId",userInfo.getUser_id()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot querySnapshot: queryDocumentSnapshots){

                    userCart.add(querySnapshot.toObject(Cart_info.class));
                }
            }
        });
    }

    //////////////////////////////////////////////////// Delete Cart///////////////////////////////////////////////////


    @Override
    public void delete_cart(Cart_info cart_info){

        for (int i=0;i<userCart.size();i++){

            if(cart_info.getCartId().matches(userCart.get(i).getCartId())){
                userCart.remove(i);
                break;
            }
        }

        userCollectionViewModel.deleteCart(cart_info);

       /* if(userCart.size()==0){
            dismiss_badge();
        }else {
            show_badge();
        }*/

       // cart_R.document(cart_info.getCartId()).delete();

    }

    ///////////////////////////////////////////////////////// Delete All Carts /////////////////////////////////////////////////////

    @Override
    public void delete_AllCarts(){

        WriteBatch batch=firebaseFirestore.batch();
        for (int i=0;i<userCart.size();i++){
            DocumentReference documentReference=cart_R.document(userCart.get(i).getCartId());
            batch.delete(documentReference);
        }

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                userCart.clear();
            }
        });




    }
    
    ////////////////////////////////////////////////// Add order////////////////////////////////////////////////////////////////
    

    @Override
    public  void add_order(final List<Cart_info> cartInfoList, final String order_st, final String order_md, final String order_dt){
        
        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
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
                    Order_info order_info=new Order_info(userInfo.getUser_id(),order_R.document().getId(),order_st,order_md,cartInfoList.get(i),order_dt,userInfo);
                    transaction.set(order_R.document(order_info.getOrder_id()),order_info);


                }



                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                delete_AllCarts();
            }
        });
        
    }





    ///////////////////////////////////////////////////// Retrieve order///////////////////////////////////////////////

    @Override
    public void retrieve_order(){

        order_R.whereEqualTo("user_id",userInfo.getUser_id()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){

                    orderInfos.add(querySnapshot.toObject(Order_info.class));
                }
            }
        });
    }


    //////////////////////////////////////////////////// Delete order///////////////////////////////////////////////////


    @Override
    public void delete_order(final Order_info order_info){


        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentReference documentReference=product_R.document(order_info.getCart_info().getPro_id());
                DocumentSnapshot documentSnapshot=transaction.get(documentReference);
                Product_info product_info=documentSnapshot.toObject(Product_info.class);
                int stoke=Integer.valueOf(product_info.getStock())+1;
                transaction.update(documentReference,"stock",stoke);
                transaction.delete( order_R.document(order_info.getOrder_id()));

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                for (int i=0;i<orderInfos.size();i++){

                    if(order_info.getOrder_id().matches(orderInfos.get(i).getOrder_id())){
                        orderInfos.remove(i);
                        break;
                    }
                }


            }
        });




    }



    //////////////////////////////////////////////////// Get user Info from firebase/////////////////////////////////////


    private void get_userInfo(String id){

        user_R.document(id.trim()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                userInfo=documentSnapshot.toObject(User_info.class);

                retrieve_likes();
                retrieve_carts();
                retrieve_order();

            }
        });

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
