package yaseerfarah22.com.ozet_design.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.View.Fragment_holder;
import yaseerfarah22.com.ozet_design.View.MainActivity;
import yaseerfarah22.com.ozet_design.View.Product_desc;

/**
 * Created by DELL on 1/17/2019.
 */

public class Card_cart_adapter extends RecyclerView.Adapter<Card_cart_adapter.Pro_holder> {

    Product_desc.OnFragmentInteractionListener connection;
    private Context context;
    private List<Cart_info> cartsInfo;
    private String flag;
    private FragmentActivity fragmentActivity;





    public Card_cart_adapter(Context context, List cartsInfo,String flag,FragmentActivity fragmentActivity) {
        this.context = context;
        this.cartsInfo=cartsInfo;
        this.flag=flag;
        this.fragmentActivity=fragmentActivity;

    }

    @NonNull
    @Override
    public Pro_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;


             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_cardview, parent, false);


        return new Pro_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Pro_holder holder, final int position) {

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag.matches("Main")){

                   MainActivity mainActivity=(MainActivity) fragmentActivity;
                   mainActivity.delete_cart(cartsInfo.get(position));
                   mainActivity.count_price();

                }else{

                    Fragment_holder fragment_holder=(Fragment_holder)fragmentActivity;
                    fragment_holder.delete_cart(cartsInfo.get(position));
                    fragment_holder.count_price();

                }

            }
        });

        Glide.with(context).load(cartsInfo.get(position).getPro_imageurl()).into(holder.imageView);
        holder.title.setText(cartsInfo.get(position).getPro_name());
        holder.price.setText(cartsInfo.get(position).getPro_price());
        holder.size.setText(cartsInfo.get(position).getSize());
        holder.quantity.setText(cartsInfo.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartsInfo.size();
    }


    private void delete(int position){
        cartsInfo.remove(position);
        notifyDataSetChanged();
    }

    //////////////////////////////////////////////////////////
    public class Pro_holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,size,quantity,price;
        ImageButton close;

        public Pro_holder(View itemView) {
            super(itemView);
            close=(ImageButton) itemView.findViewById(R.id.card_close);
           imageView =(ImageView) itemView.findViewById(R.id.card_image);
           title=(TextView) itemView.findViewById(R.id.card_title);
            size=(TextView) itemView.findViewById(R.id.card_ansize);
            quantity=(TextView) itemView.findViewById(R.id.card_anquantity);
            price=(TextView) itemView.findViewById(R.id.card_price);

        }
    }


}
