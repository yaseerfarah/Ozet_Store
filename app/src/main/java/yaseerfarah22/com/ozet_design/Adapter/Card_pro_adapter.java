package yaseerfarah22.com.ozet_design.Adapter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import yaseerfarah22.com.ozet_design.DatabaseMethod;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.View.Product_desc;
import yaseerfarah22.com.ozet_design.View.home;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;

import static yaseerfarah22.com.ozet_design.View.MainActivity.userLikes;

/**
 * Created by DELL on 1/17/2019.
 */

public class Card_pro_adapter extends RecyclerView.Adapter<Card_pro_adapter.Pro_holder> {

    Product_desc.OnFragmentInteractionListener connection;
    DatabaseMethod databaseMethod;
    private Context context;
    private List<Product_info> products=new ArrayList<>();
    int layout_view=0;
    private Likes likes=null;
    private FragmentActivity fragmentActivity;
    private UserCollectionViewModel userCollectionViewModel;
    private Fragment fragment;


    public Card_pro_adapter(Context context, List<Product_info> products, UserCollectionViewModel userCollectionViewModel, int layout_view, FragmentActivity activity, Fragment fragment) {
        this.context = context;
        this.products = products;
        this.userCollectionViewModel=userCollectionViewModel;
        this.layout_view=layout_view;
        this.connection=(Product_desc.OnFragmentInteractionListener) activity;
        this.databaseMethod=(DatabaseMethod)activity;
        this.fragmentActivity=activity;
        this.fragment=fragment;
    }




    @NonNull
    @Override
    public Pro_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(layout_view==0) {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            ButterKnife.bind(this,view);
        }else {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider, parent, false);
            ButterKnife.bind(this,view);
        }

        return new Pro_holder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final Pro_holder holder, final int position) {
        Glide.with(context).load(products.get(position).getImages_url().get(0))
                .apply(new RequestOptions().placeholder(R.mipmap.logo))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.pro_image.setImageDrawable(resource);

                    }
                });

        for (int i=0;i<userLikes.size();i++){
            if(products.get(position).getId().matches(userLikes.get(i).getProduct_info().getId())){
                likes=userLikes.get(i);
                holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_white_24dp));
            }
        }
       /* userCollectionViewModel.getLikeLiveData().observe(fragment, new Observer<List<Likes>>() {
            @Override
            public void onChanged(@Nullable List<Likes> likesList) {
                for (int i=0;i<likesList.size();i++){
                    if(products.get(position).getId().matches(likesList.get(i).getProduct_info().getId())){
                        likes=likesList.get(i);
                        holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_white_24dp));
                    }
                }
            }
        });*/



        holder.pro_image.setTransitionName(products.get(position).getImages_url().get(0));

        holder.name.setText(products.get(position).getName());
        holder.price.setText(products.get(position).getPrice());

        holder.fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.fav_button.getBackground().getConstantState()== ContextCompat.getDrawable(context,R.drawable.ic_favorite_white_24dp).getConstantState()){
                    holder.fav_button.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    databaseMethod.delete_like(likes);

                }
                else {
                    // holder.fav_button.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
                    holder.fav_button.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_favorite_white_24dp));
                    databaseMethod.add_like(products.get(position));
                }

            }
        });



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                connection.onFragmentInteraction(products.get(position),holder.pro_image);
                if (layout_view==1){
                    if(fragmentActivity.getSupportFragmentManager().findFragmentByTag("Home")!=null){
                        home home_=(home) fragmentActivity.getSupportFragmentManager().findFragmentByTag("Home");
                        home_.cancel_timer();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    //////////////////////////////////////////////////////////
    public class Pro_holder extends RecyclerView.ViewHolder{

        ImageView pro_image;
        TextView name,price;
        ImageButton fav_button;
        CardView cardView;

        public Pro_holder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            pro_image=(ImageView)itemView.findViewById(R.id.pro_image);
            name=(TextView)itemView.findViewById(R.id.pro_name);
            price=(TextView)itemView.findViewById(R.id.pro_price);
            fav_button=(ImageButton)itemView.findViewById(R.id.fav_button);

        }
    }


}
