package yaseerfarah22.com.ozet_design.Adapter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.View.Product_desc;
import yaseerfarah22.com.ozet_design.View.home;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;

/**
 * Created by DELL on 1/17/2019.
 */

public class Recycleview_Cart_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Product_desc.OnFragmentInteractionListener connection;
    private Context context;
    private List<String>titles;
    private List<String>flags;
    private List<Integer>icons;
    private List<String> slider_image;
    private UserCollectionViewModel userCollectionViewModel;
    private OzetViewModel ozetViewModel;
    private Fragment fragment;
    private FragmentActivity fragmentActivity;
    private RecyclerView.RecycledViewPool viewPool;
    private final int first=1;
    private final int other=2;
    private Timer timer;
    private int page=0;
    private TextView[] dots;


    public Recycleview_Cart_Adapter(Fragment fragment,FragmentActivity fragmentActivity, Context context, List<String> titles, List<String> flags, List<Integer> icons,List<String> slider_image,int page, UserCollectionViewModel userCollectionViewModel, OzetViewModel ozetViewModel) {
        this.fragment=fragment;
        this.fragmentActivity=fragmentActivity;
        this.connection = (Product_desc.OnFragmentInteractionListener)fragmentActivity;
        this.context = context;
        this.titles = titles;
        this.flags = flags;
        this.icons = icons;
        this.userCollectionViewModel = userCollectionViewModel ;
        this.ozetViewModel = ozetViewModel;
        viewPool=new RecyclerView.RecycledViewPool();
        this.slider_image=slider_image;
        this.page=page;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType==first){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_viewpager_cart, parent, false);
            return new ViewPager_holder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_cart, parent, false);
            return new Rec_holder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder_class, final int position) {

        if(holder_class instanceof ViewPager_holder){

            final ViewPager_holder holder=(ViewPager_holder)holder_class;


            final Slider_Adapter sliderAdapter=new Slider_Adapter(context,slider_image,0,fragmentActivity.getResources(),fragmentActivity);
            holder.viewPager.setAdapter(sliderAdapter);
            Add_Dots(context,page,sliderAdapter,holder.linearLayout);
           holder.viewPager.setCurrentItem(page);
            viewpager_timer(5,holder.viewPager);
            holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    page=position;
                    Add_Dots(context,position,sliderAdapter,holder.linearLayout);


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });




        }



        if(holder_class instanceof Rec_holder)
        {
            Rec_holder holder=(Rec_holder)holder_class;
            holder.textView.setText(titles.get(position));
            holder.imageView.setImageResource(icons.get(position));
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


            holder.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            holder.recyclerView.setRecycledViewPool(viewPool);

            final List<Product_info>[] product_infoList = new List[1];
            product_infoList[0] = new ArrayList<>();
            final Card_pro_adapter cardProAdapter = new Card_pro_adapter(context, product_infoList[0], userCollectionViewModel, 1, fragmentActivity, fragment);
            holder.recyclerView.setAdapter(cardProAdapter);

            ozetViewModel.getProductNewLiveData().observe(fragment, new Observer<List<Product_info>>() {
                @Override
                public void onChanged(@Nullable List<Product_info> product_infos) {

                    product_infoList[0].addAll(product_infos);


                    cardProAdapter.notifyDataSetChanged();


                }
            });
        }


    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return first;
        }
        else {
            return other;
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }





    public int getPage(){
        return page;
    }


    //////////////////////////////////////////////////////////
    public class Rec_holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        RecyclerView recyclerView;
        Button button;
        public Rec_holder(View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById( R.id.cat_logo1);
            textView=(TextView) itemView.findViewById( R.id.cat_title1);
            recyclerView=(RecyclerView) itemView.findViewById(R.id.cat_recycle1);
            button=(Button) itemView.findViewById(R.id.seemore);

        }
    }



    //////////////////////////////////////////////////////////
    public class ViewPager_holder extends RecyclerView.ViewHolder{

        ViewPager viewPager;
        LinearLayout linearLayout;
        public ViewPager_holder(View itemView) {
            super(itemView);

            viewPager=(ViewPager)itemView.findViewById(R.id.view_pager);
           // viewPager.setCurrentItem(0);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear);

        }
    }



    //////////////////////////////// inner Class for Timer Task//////////////////////////////////////////////


    class Timer_task extends TimerTask {

        ViewPager viewPager;
        public Timer_task(ViewPager viewPager) {
            this.viewPager=viewPager;
        }

        @Override
        public void run() {

            if(fragmentActivity!=null){

                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(page>=2){
                            page=0;
                        }
                        else {
                            page++;
                        }

                        viewPager.setCurrentItem(page);

                    }
                });
            }



        }
    }

    ////////// cancel Timer//////////////////

    public void cancel_timer(){

        timer.cancel();
        timer=null;
    }


    /// timer for viewpager///////////

    private void viewpager_timer(int second,ViewPager viewPager){

        // page=viewPager.getCurrentItem();
        // Toast.makeText(getActivity(),"timer",Toast.LENGTH_LONG).show();
        timer=new Timer();
        timer.schedule(new Timer_task(viewPager),5*1000,second*1000);



    }


    public void Add_Dots(Context context, int i,Slider_Adapter sliderAdapter,LinearLayout linearLayout){
        dots=new TextView[sliderAdapter.getCount()];
        linearLayout.removeAllViews();
        for (int b=0;b<dots.length;b++){
            dots[b]=new TextView(context);
            dots[b].setText(Html.fromHtml("&#8226"));
            dots[b].setTextSize(35);
            dots[b].setTextColor(fragmentActivity.getResources().getColor(R.color.grey));
            linearLayout.addView(dots[b]);
        }

        dots[i].setTextColor(Color.WHITE);

    }


}
