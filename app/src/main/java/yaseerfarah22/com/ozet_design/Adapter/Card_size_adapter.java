package yaseerfarah22.com.ozet_design.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
    private Button lastCheck;

    int button_index=0;




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

        if(button_index==position){
            lastCheck=holder.button;
            scaleUpView(lastCheck);
            lastCheck.setBackground(ContextCompat.getDrawable(context,R.drawable.light_black));
            lastCheck.setTextColor(ContextCompat.getColor(context, R.color.white));

        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_index!=position) {
                    size = holder.button.getText().toString();
                    button_index = position;
                    scaleUpView(holder.button);
                    holder.button.setBackground(ContextCompat.getDrawable(context, R.drawable.light_black));
                    holder.button.setTextColor(ContextCompat.getColor(context, R.color.white));


                    scaleDownView(lastCheck);
                    lastCheck.setBackground(ContextCompat.getDrawable(context, R.drawable.border_b2));
                    lastCheck.setTextColor(ContextCompat.getColor(context, R.color.black));

                    lastCheck = holder.button;
                    //notifyDataSetChanged();

                }

            }

        });





    }



    ///////////////////////////////////////// Scale Animation///////////////////////////////////////////////////////

    public void scaleUpView(View v) {
        Animation anim = new ScaleAnimation(
                1f, 1.1f, // Start and end values for the X axis scaling
                1f, 1.1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(300);
        v.startAnimation(anim);
    }

    public void scaleDownView(View v) {
        Animation anim = new ScaleAnimation(
                1.1f, 1f, // Start and end values for the X axis scaling
                1.1f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(300);
        v.startAnimation(anim);
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
