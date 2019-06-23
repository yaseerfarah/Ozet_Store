package yaseerfarah22.com.ozet_design.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.auth.data.model.Resource;

import java.util.List;

import yaseerfarah22.com.ozet_design.R;

/**
 * Created by DELL on 1/19/2019.
 */

public class Slider_Adapter extends PagerAdapter {
    private Context context;
    private int layout=3;
    private List<String> slider_image;
    private List<Bitmap> slider_image2;
    private Resources resource;
    private FragmentActivity fragmentActivity;
    private Drawable drawable;
   // private Drawable[] drawable;


    public Slider_Adapter(Context context, List<String> slider_image, int layout, Resources resource, FragmentActivity activity) {
        this.context = context;
        this.slider_image=slider_image;
        this.layout=layout;
        this.resource=resource;
        this.fragmentActivity=activity;
    }


    public Slider_Adapter(Context context, List<String> slider_image, int layout, Resources resource,Drawable drawable, FragmentActivity activity) {
        this.context = context;
        this.slider_image=slider_image;
        this.layout=layout;
        this.resource=resource;
        this.drawable=drawable;
        this.fragmentActivity=activity;
    }


    public Slider_Adapter(Context context, List<Bitmap> slider_image) {
        this.context = context;
        this.slider_image2=slider_image;
    }

    @Override
    public int getCount() {
        if(layout==3){
            return slider_image2.size();
        }
        else {
        return slider_image.size();}
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view;
        if(layout==0) {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider, container, false);

            ImageView imageView=(ImageView) view.findViewById(R.id.image_slider);
            Glide.with(context).load(slider_image.get(position))
                    .into(imageView);

        }

        else if(layout==3) {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.pro_slide, container, false);

            ImageView imageView=(ImageView) view.findViewById(R.id.image_slider);
            ImageView imageView2=(ImageView) view.findViewById(R.id.image_slider2);
            imageView2.setImageBitmap(slider_image2.get(position));
            imageView.setImageBitmap(createBlurBitmap(slider_image2.get(position),25));



        }


        else {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.pro_slide, container, false);



             final ImageView imageView=(ImageView) view.findViewById(R.id.image_slider);


            final ImageView imageView2=(ImageView) view.findViewById(R.id.image_slider2);

            imageView2.setTransitionName(slider_image.get(position));
            if(position==0){
                imageView2.setImageDrawable(drawable);
            }else {


                Glide.with(context).load(slider_image.get(position))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                imageView2.setImageDrawable(resource);
                                //fragmentActivity.startPostponedEnterTransition();
                            }
                        });
            }

           // imageView2.setImageDrawable(drawable[position]);




           /* Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        if (glide.getView().getDrawable()!=null){
                            Dali.create(context).load((BitmapDrawable)glide.getView().getDrawable() ).blurRadius(12).into(imageView);
                            break;
                        }
                    }

                }
            });
            thread.start();*/

           //Blurry.with(context).radius(25).sampling(2).capture(imageView2).into(imageView);

           // Dali.create(context).load(R.drawable.man).blurRadius(12).into(imageView);

           // Bitmap bitmap= BitmapFactory.decodeResource(resource,R.mipmap.cloth);



            Glide.with(context)
                    .asBitmap()
                    .load(slider_image.get(position))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(createBlurBitmap(resource,25));
                        }
                    });







        }




        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }



    private Bitmap createBlurBitmap(Bitmap src, float r) {
        if (r <= 0) {
            r = 0.1f;
        } else if (r > 25) {
            r = 25.0f;
        }

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(r);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
    }
}
