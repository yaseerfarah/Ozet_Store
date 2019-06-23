package yaseerfarah22.com.ozet_design.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import yaseerfarah22.com.ozet_design.R;

/**
 * Created by DELL on 1/19/2019.
 */

public class Credit_Card_Adapter extends PagerAdapter {
    private Context context;
   private String number,name,mm,yy,space;
   private TextView number_t,name_t,mm_t,yy_t;
   private int background[]={
           R.mipmap.ic_card_bg,
           R.mipmap.red_b,
           R.mipmap.blue_b,
           R.mipmap.green_b,
           R.mipmap.gold_b
   };
   ImageButton imageButton;


    public Credit_Card_Adapter(Context context, String number, String name, String mm, String yy) {
        this.context = context;
        this.number = number;
        this.name = name;
        this.mm = mm;
        this.yy = yy;
    }

    @Override
    public int getCount() {

        return background.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout)object;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.credit_card, container, false);
        //Toast.makeText(context,name,Toast.LENGTH_LONG).show();



        number_t=(TextView)view.findViewById(R.id.credit_num);
        name_t=(TextView)view.findViewById(R.id.credit_name);
        yy_t=(TextView)view.findViewById(R.id.credit_dateyy);
        mm_t=(TextView)view.findViewById(R.id.credit_datemm);
        imageButton=(ImageButton)view.findViewById(R.id.card_bg);
        imageButton.setBackgroundResource(background[position]);
        space="    ";
        String whole_number="";
        int diffrence=16-number.trim().length();
        int letter_count=1;



        if(number.trim().length()==0){
            number_t.setText("XXXX    XXXX    XXXX    XXXX");
        }
        else {
            for(int i=0;i<number.trim().length();i++){
                if(letter_count==5){
                    whole_number+=space;
                    letter_count=1;

                }

                    whole_number+=number.charAt(i);




                letter_count++;

            }


            for(int i=0;i<diffrence;i++){
                if(letter_count==5){
                    whole_number+=space;
                    letter_count=1;

                }

                    whole_number+="X";


                letter_count++;

            }



            number_t.setText(whole_number);

        }

        if(name.trim().length()==0){
            name_t.setText("YASSER FARAH");
        }
        else {
            name_t.setText(name);
        }

        if(mm.trim().length()==0){
            mm_t.setText("XX");
        }
        else {
            mm_t.setText(mm);
        }

        if(yy.trim().length()==0){
            yy_t.setText("XX");
        }
        else {
            yy_t.setText(yy);
        }


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }




}
