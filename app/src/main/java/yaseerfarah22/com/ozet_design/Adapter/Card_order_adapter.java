package yaseerfarah22.com.ozet_design.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import yaseerfarah22.com.ozet_design.Product;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.View.Product_desc;

/**
 * Created by DELL on 1/17/2019.
 */

public class Card_order_adapter extends RecyclerView.Adapter<Card_order_adapter.Pro_holder> {

    Product_desc.OnFragmentInteractionListener connection;
    private Context context;
    private List<Product> ordersInfo;





    public Card_order_adapter(Context context, List ordersInfo) {
        this.context = context;
        this.ordersInfo=ordersInfo;

    }

    @NonNull
    @Override
    public Pro_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;


             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cardview, parent, false);


        return new Pro_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Pro_holder holder, final int position) {

        Glide.with(context).load(ordersInfo.get(position).getPro_image()).into(holder.imageView);
        holder.title.setText(ordersInfo.get(position).getName());
        holder.price.setText(ordersInfo.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return ordersInfo.size();
    }


    //////////////////////////////////////////////////////////
    public class Pro_holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,price;

        public Pro_holder(View itemView) {
            super(itemView);
           imageView =(ImageView) itemView.findViewById(R.id.order_image);
           title=(TextView) itemView.findViewById(R.id.order_cardtitle);
            price=(TextView) itemView.findViewById(R.id.order_antotalprice);

        }
    }


}
