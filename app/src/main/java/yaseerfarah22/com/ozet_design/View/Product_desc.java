package yaseerfarah22.com.ozet_design.View;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Slide;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import yaseerfarah22.com.ozet_design.Adapter.Card_size_adapter;
import yaseerfarah22.com.ozet_design.DatabaseMethod;
import yaseerfarah22.com.ozet_design.Model.Array_Helper;
import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.Adapter.Slider_Adapter;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;


public class Product_desc extends Fragment {


    ViewPager viewPager;
    Button add_to_cart;
    ImageButton navigation,cart;
    RecyclerView recyclerView;
    Slider_Adapter sliderAdapter;
    TextView price,exprice;
   Product_info product;
   ImageButton nav_icon,like;
   Card_size_adapter cardSizeAdapter;
   Button quantity_plus,quantity_minus;
   TextView quantity_text;
    String flag;
    int quantity=1;
    Likes likes=null;
    TextView cart_badge_text;
    ImageView pro_image;



   String[]sizes={
           "S",
           "M",
           "L",
           "XL"
   };
    private static final long MOVE_DEFAULT_TIME = 200;
    private static final long FADE_DEFAULT_TIME = 300;

    @Inject
    ViewModelFactory viewModelFactory;



    public Product_desc() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(this);

        Slide enterFade = new Slide();

        enterFade.setStartDelay(MOVE_DEFAULT_TIME);
        enterFade.setDuration(FADE_DEFAULT_TIME);
        setEnterTransition(enterFade);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_product_desc, container, false);


        postponeEnterTransition();

        quantity_plus=(Button)view.findViewById(R.id.quantity_plus);
        quantity_minus=(Button)view.findViewById(R.id.quantity_minus);
        quantity_text=(TextView) view.findViewById(R.id.quantity_text);
        navigation=(ImageButton)view.findViewById(R.id.desc_nav);
        cart=(ImageButton)view.findViewById(R.id.desc_cart);
        like=(ImageButton)view.findViewById(R.id.desc_like);
        cart_badge_text=(TextView)view.findViewById(R.id.desc_badge_text);
        product=(Product_info) getArguments().getSerializable("key");
        Array_Helper arrayHelper=(Array_Helper) getArguments().getSerializable("Image");
        pro_image=(ImageView) arrayHelper.getImageView();
        List<String> images=product.getImages_url();
       // Drawable[]drawables={product.getDrawable()};
        viewPager=(ViewPager)view.findViewById(R.id.v_view_pager);
        sliderAdapter =new Slider_Adapter(view.getContext(),images,1,getResources(),pro_image.getDrawable(),getActivity());
        viewPager.setAdapter(sliderAdapter);
       /* final ImageView imageView2=(ImageView) view.findViewById(R.id.v_view_pager);
        imageView2.setTransitionName(product.getPro_image());
       // Toast.makeText(view.getContext(),imageView2.getTransitionName(),Toast.LENGTH_SHORT).show();
        Glide.with(view.getContext()).load(product.getPro_image())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView2.setImageDrawable(resource);
                        startPostponedEnterTransition();
                    }
                });
*/



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

        for (int i = 0; i< MainActivity.userLikes.size(); i++){
            if(product.getId().matches(MainActivity.userLikes.get(i).getLikeId())){
                likes= MainActivity.userLikes.get(i);
                like.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_white_24dp));
                like.setBackgroundTintList(getResources().getColorStateList(R.color.black));
            }
        }

       quantity_plus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               quantity++;
               quantity_text.setText(String.valueOf(quantity));
           }
       });

       quantity_minus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (quantity>1){
                   quantity--;
                   quantity_text.setText(String.valueOf(quantity));
               }
           }
       });

       like.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               DatabaseMethod databaseMethod=(DatabaseMethod)getActivity();



               if(likes!=null){
                   like.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                   like.setBackgroundTintList(getResources().getColorStateList(R.color.black));

                  databaseMethod.delete_like(likes);

               }
               else {
                   // holder.fav_button.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
                   like.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_white_24dp));
                   like.setBackgroundTintList(getResources().getColorStateList(R.color.black));


                   databaseMethod.add_like(product);
               }
           }
       });


        price=(TextView) view.findViewById(R.id.vprice);
        exprice=(TextView) view.findViewById(R.id.vexprice);
        price.setText(product.getPrice());



        //Recycler view//

        recyclerView=(RecyclerView)view.findViewById(R.id.vrecycleview);
        cardSizeAdapter=new Card_size_adapter(view.getContext(),product.getSizes());
        recyclerView.setAdapter(cardSizeAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

         flag=getArguments().getString("Flag");

        if(flag.matches("Main")){
            final MainActivity conn=(MainActivity) getActivity();
            navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    conn.open_drawer();
                }
            });

            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    conn.Cart_fragment();
                }
            });

        }
        else {
            navigation.setVisibility(View.INVISIBLE);
            final Fragment_holder conn=(Fragment_holder) getActivity();
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    conn.Cart_fragment();
                }
            });
        }

        add_to_cart=(Button)view.findViewById(R.id.desc_add_tocart);
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_dialog();
            }
        });





        return view;
    }



    private void create_dialog(){


        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_succ);
        TextView size=(TextView)dialog.findViewById(R.id.dialog_ansize);
        TextView quan=(TextView)dialog.findViewById(R.id.dialog_anquantity);
        TextView price=(TextView)dialog.findViewById(R.id.dialog_price);
        size.setText(cardSizeAdapter.getSize());
        quan.setText(String.valueOf(quantity));
        price.setText(product.getPrice());

        FloatingActionButton submit=(FloatingActionButton) dialog.findViewById(R.id.dialog_submit);
        ImageButton close=(ImageButton)dialog.findViewById(R.id.dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.matches("Main")){
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.add_to_cart(cardSizeAdapter.getSize(),String.valueOf(quantity),product);

                    dialog.dismiss();

                }
                else {
                    Fragment_holder fragment_holder=(Fragment_holder)getActivity();
                    fragment_holder.add_to_cart(cardSizeAdapter.getSize(),String.valueOf(quantity),product);
                    dialog.dismiss();
                }
               // show_badge();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();





    }

    /////////////////////////////////////////////////////////

    private void show_badge(int size){

        cart_badge_text.setVisibility(View.VISIBLE);
        cart_badge_text.setText(String.valueOf(size));
    }

    ///////////////////////////////////////////////////////////


    private void dismiss_badge(){

        cart_badge_text.setVisibility(View.GONE);
        cart_badge_text.setText(String.valueOf(MainActivity.userCart.size()));
    }



    public void set_adapt(){
        recyclerView.setAdapter(cardSizeAdapter);
    }


    public void setProduct(Product_info product){
        this.product=product;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Product_info product,View imageView);



    }
}
