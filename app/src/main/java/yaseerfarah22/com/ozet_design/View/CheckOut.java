package yaseerfarah22.com.ozet_design.View;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.Order_info;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;

import static yaseerfarah22.com.ozet_design.View.MainActivity.userCart;
import static yaseerfarah22.com.ozet_design.View.MainActivity.userInfo;

public class CheckOut extends AppCompatActivity implements Payment.OnFragmentInteractionListener {


    String orderMethod;
    Map<String,String> order_field;
    Map<String,String> shipping_field;
    ImageButton close,shipping_b,shipping_f,payment_b,payment_f,done_b,done_f;
    View view1,view2;
    Button next;
    FrameLayout frameLayout;
    Shipping shipping;
    Payment payment;
    Done done;


    private FirebaseFirestore firebaseFirestore;
    private CollectionReference user_R;
    private CollectionReference product_R;
    private CollectionReference likes_R;
    private CollectionReference order_R;
    private CollectionReference cart_R;


    @Inject
    ViewModelFactory viewModelFactory;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


        AndroidInjection.inject(this);



        firebaseFirestore=FirebaseFirestore.getInstance();
        user_R=firebaseFirestore.collection("User");
        product_R=firebaseFirestore.collection("Product");
        likes_R=firebaseFirestore.collection("Likes");
        order_R=firebaseFirestore.collection("Order");
        cart_R=firebaseFirestore.collection("Cart");


        view1=(View) findViewById(R.id.from_shipp_to_pay);
        view2=(View) findViewById(R.id.from_pay_to_done);
        close=(ImageButton)findViewById(R.id.check_close);
        shipping_b=(ImageButton)findViewById(R.id.shipping_back);
        shipping_f=(ImageButton)findViewById(R.id.shipping);
        payment_b=(ImageButton)findViewById(R.id.payment_back);
        payment_f=(ImageButton)findViewById(R.id.payment);
        done_b=(ImageButton)findViewById(R.id.done_back);
        done_f=(ImageButton)findViewById(R.id.done);
        next=(Button) findViewById(R.id.check_next);
        frameLayout=(FrameLayout) findViewById(R.id.box2);

        start_fragment();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                check_out_track();
            }
        });




    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void check_out_track(){

        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();

        if(getSupportFragmentManager().findFragmentByTag("Shipping")!=null&&getSupportFragmentManager().findFragmentByTag("Shipping").isVisible()){
          Shipping shipping=(Shipping) getSupportFragmentManager().findFragmentByTag("Shipping");
           if (shipping.is_ok()){

               shipping_field=shipping.get_Field();

               payment=new Payment();
               fragmentTransaction.replace(R.id.box2,payment,"Payment");
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
               shipping_f.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
               view1.setBackgroundResource(R.color.black);
               payment_b.setBackgroundTintList(getResources().getColorStateList(R.color.white));
               payment_f.setVisibility(View.VISIBLE);
           }
           else {
               Toast.makeText(CheckOut.this,"something Wrong",Toast.LENGTH_LONG).show();
           }



        }else  if(getSupportFragmentManager().findFragmentByTag("Payment")!=null&&getSupportFragmentManager().findFragmentByTag("Payment").isVisible()){
           Payment payment=(Payment)getSupportFragmentManager().findFragmentByTag("Payment");
           orderMethod=payment.get_OrderMethod();
           done=new Done();
            if(orderMethod.matches("Cash")){

                fragmentTransaction.replace(R.id.box2,done,"Done");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                next.setText("Done");
                payment_f.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
                view2.setBackgroundResource(R.color.black);
                done_b.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                done_f.setVisibility(View.VISIBLE);
                done_f.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
            }
            else if(orderMethod.matches("Credit")||orderMethod.matches("Paypal")){

                if(payment.is_ok(orderMethod)){

                    order_field=payment.get_Field(orderMethod);

                    fragmentTransaction.replace(R.id.box2,done,"Done");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    next.setText("Done");
                    payment_f.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
                    view2.setBackgroundResource(R.color.black);
                    done_b.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                    done_f.setVisibility(View.VISIBLE);
                    done_f.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);

                }
                else {
                    Toast.makeText(CheckOut.this,"something Wrong",Toast.LENGTH_LONG).show();
                }

            }





        } else  if(getSupportFragmentManager().findFragmentByTag("Done")!=null&&getSupportFragmentManager().findFragmentByTag("Done").isVisible()){


            SimpleDateFormat order_dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String order_date=order_dateFormat.format(Calendar.getInstance().getTime());

            add_order(userCart,"Pinding",orderMethod,order_date);


        }


    }


    private void start_fragment(){
        shipping=new Shipping();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.box2,shipping,"Shipping");
        fragmentTransaction.commit();
    }


    private void set_fragment(Fragment fragment,String flag){
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.box2,fragment,flag);
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag("Shipping")!=null&&getSupportFragmentManager().findFragmentByTag("Shipping").isVisible()){

            super.onBackPressed();



        }else  if(getSupportFragmentManager().findFragmentByTag("Payment")!=null&&getSupportFragmentManager().findFragmentByTag("Payment").isVisible()){

            shipping_f.setBackgroundResource(R.drawable.ic_radio_button_unchecked_black_24dp);
            view1.setBackgroundResource(R.color.grey);
            payment_b.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
            payment_f.setVisibility(View.INVISIBLE);
            super.onBackPressed();



        } else  if(getSupportFragmentManager().findFragmentByTag("Done")!=null&&getSupportFragmentManager().findFragmentByTag("Done").isVisible()){

            next.setText("Next");
            payment_f.setBackgroundResource(R.drawable.ic_radio_button_unchecked_black_24dp);
            view2.setBackgroundResource(R.color.grey);
            done_b.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
            done_f.setVisibility(View.INVISIBLE);
            super.onBackPressed();



        }


    }


    @Override
    public void append_pram(String name, int i) {
        if(getSupportFragmentManager().findFragmentByTag("Payment")!=null)
        {
            Payment payment=(Payment) getSupportFragmentManager().findFragmentByTag("Payment");

            switch (i){
                case 1:
                    payment.add_name(name);
                    break;
                case 2:
                    payment.add_number(name);
                    break;

                case 3:
                    payment.add_mm(name);
                    break;

                case 4:
                    payment.add_yy(name);
                    break;

                case 5:
                    payment.add_cvv(name);
                    break;
            }
        }
    }


    ////////////////////////////////////////////////// Add order////////////////////////////////////////////////////////////////


    public  void add_order(final List<Cart_info> cartInfoList, final String order_st, final String order_md, final String order_dt){

        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                for (int i=0;i<cartInfoList.size();i++){
                    DocumentReference documentReference=product_R.document(cartInfoList.get(i).getPro_id());
                    DocumentSnapshot documentSnapshot=transaction.get(documentReference);
                    Product_info product_info=documentSnapshot.toObject(Product_info.class);
                    int pur=Integer.valueOf(product_info.getPurchase())+1;
                    int stoke=Integer.valueOf(product_info.getStock())-1;
                    transaction.update(documentReference,"purchase",pur);
                    transaction.update(documentReference,"stock",stoke);
                    Order_info order_info=new Order_info(userInfo.getUser_id(),order_R.document().getId(),order_st,order_md,cartInfoList.get(i),order_dt,userInfo);
                    transaction.set(order_R.document(order_info.getOrder_id()),order_info);


                }



                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                delete_AllCarts();
            }
        });

    }

    ///////////////////////////////////////////////////////// Delete All Carts /////////////////////////////////////////////////////

    public void delete_AllCarts(){

        WriteBatch batch=firebaseFirestore.batch();
        for (int i=0;i<userCart.size();i++){
            DocumentReference documentReference=cart_R.document(userCart.get(i).getCartId());
            batch.delete(documentReference);
        }

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                userCart.clear();
                startActivity(new Intent(CheckOut.this,Order_.class));

                finish();
            }
        });




    }



}
