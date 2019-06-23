package yaseerfarah22.com.ozet_design.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.View.MainActivity;

/**
 * Created by DELL on 1/17/2019.
 */

public class Category_card_adapter extends RecyclerView.Adapter<Category_card_adapter.Pro_holder> {


    private Context context;
    private MainActivity activity;

    private int[] image={
            R.mipmap.cover,
            R.mipmap.shoes2,
            R.mipmap.pants
    };
    private String[] Name={
          "Clothes",
            "Shoes",
            "Pants"
    };




    public Category_card_adapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity= (MainActivity) activity;
    }

    @NonNull
    @Override
    public Pro_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_view, parent, false);


        return new Pro_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pro_holder holder, final int position) {
        holder.category_image.setImageResource(image[position]);
        holder.name.setText(Name[position]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.start_zctivity(Name[position]);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Name.length;
    }


    //////////////////////////////////////////////////////////
    public class Pro_holder extends RecyclerView.ViewHolder{

        ImageView category_image;
        TextView name;
        CardView cardView;

        public Pro_holder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.category_cardview);
            category_image=(ImageView)itemView.findViewById(R.id.category_image);
            name=(TextView)itemView.findViewById(R.id.category_name);


        }
    }


}
