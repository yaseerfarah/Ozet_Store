package yaseerfarah22.com.ozet_design.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.View.Product_desc;

/**
 * Created by DELL on 1/17/2019.
 */

public class Card_size_adapter extends RecyclerView.Adapter<Card_size_adapter.Pro_holder> {

    Product_desc.OnFragmentInteractionListener connection;
    private Context context;
    private List<String>sizes;
    private String size;

    int button_index=-1;




    public Card_size_adapter(Context context,List<String>sizes) {
        this.context = context;
        this.sizes=sizes;
        this.size=sizes.get(0);

    }

    @NonNull
    @Override
    public Pro_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;


             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_size, parent, false);


        return new Pro_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Pro_holder holder, final int position) {

        holder.button.setText(String.valueOf(sizes.get(position)));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size=holder.button.getText().toString();
                button_index=position;
                notifyDataSetChanged();
            }

        });
        if(button_index==position){
            holder.button.setBackground(ContextCompat.getDrawable(context,R.color.black));
            holder.button.setTextColor(ContextCompat.getColor(context, R.color.white));

        }
        else {
            holder.button.setBackground(ContextCompat.getDrawable(context,R.drawable.border_b2));
            holder.button.setTextColor(ContextCompat.getColor(context, R.color.black));

        }




    }


    public String getSize(){
        return size;
    }

    @Override
    public int getItemCount() {
        return sizes.size();
    }


    //////////////////////////////////////////////////////////
    public class Pro_holder extends RecyclerView.ViewHolder{

        Button button;

        public Pro_holder(View itemView) {
            super(itemView);
           button =(Button)itemView.findViewById(R.id.sizebutton);

        }
    }


}
