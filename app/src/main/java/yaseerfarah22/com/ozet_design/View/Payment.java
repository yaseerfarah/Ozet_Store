package yaseerfarah22.com.ozet_design.View;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import yaseerfarah22.com.ozet_design.Adapter.Credit_Card_Adapter;
import yaseerfarah22.com.ozet_design.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Payment extends Fragment {


    String name,number,mm,yy,cvv;
    String orderMethod="Cash",paypal_Emailtext;
    ViewPager viewPager;
    CardView cash,paypal,credit;
    FrameLayout frameLayout;
    ImageButton cash_ic,paypal_ic,credit_ic;
    TextView  cash_t,paypal_t,credit_t;


    public Payment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payment, container, false);

        name="";
        number="";
        mm="";
        yy="";

        cash_ic=(ImageButton) view.findViewById(R.id.cash_icon);
        paypal_ic=(ImageButton) view.findViewById(R.id.pay_icon);
        credit_ic=(ImageButton) view.findViewById(R.id.credit_icon);
        credit_t=(TextView)view.findViewById(R.id.credit_text);
        cash_t=(TextView)view.findViewById(R.id.cash_text);
        paypal_t=(TextView)view.findViewById(R.id.pay_text);
        viewPager=(ViewPager)view.findViewById(R.id.bank_card);
        cash=(CardView)view.findViewById(R.id.cash_method);
        paypal=(CardView)view.findViewById(R.id.paypal_method);
        credit=(CardView)view.findViewById(R.id.credit_method);
        frameLayout=(FrameLayout) view.findViewById(R.id.payment_form);
        Credit_Card_Adapter creditCardAdapter=new Credit_Card_Adapter(view.getContext(),number,name,mm,yy);
        viewPager.setAdapter(creditCardAdapter);


        cash.setBackgroundResource(R.color.black);
        cash_t.setTextColor(getResources().getColor(R.color.white));
        cash_ic.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        frameLayout.setVisibility(View.INVISIBLE);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderMethod="Cash";
                allWhite(paypal,paypal_ic,paypal_t);
                allWhite(credit,credit_ic,credit_t);
                cash.setBackgroundResource(R.color.black);
                cash_t.setTextColor(getResources().getColor(R.color.white));
                cash_ic.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                frameLayout.setVisibility(View.INVISIBLE);
            }
        });



        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderMethod="Credit";
                allWhite(cash,cash_ic,cash_t);
                allWhite(paypal,paypal_ic,paypal_t);
                credit.setBackgroundResource(R.color.black);
                credit_ic.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                credit_t.setTextColor(getResources().getColor(R.color.white));
                Credit_Card_payment creditCardPayment=new Credit_Card_payment();
                place_fragment(creditCardPayment,"Credit");

            }
        });



        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderMethod="Paypal";
                allWhite(cash,cash_ic,cash_t);
                allWhite(credit,credit_ic,credit_t);
                paypal.setBackgroundResource(R.color.black);
                paypal_t.setTextColor(getResources().getColor(R.color.white));
                paypal_ic.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                PayPal_payment payPalPayment=new PayPal_payment();
                place_fragment(payPalPayment,"Paypal");
            }
        });



        return view;

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void allWhite(CardView cardView,ImageButton imageButton,TextView textView){
        cardView.setBackgroundResource(R.color.white);
        textView.setTextColor(getResources().getColor(R.color.black));
        imageButton.setBackgroundTintList(getResources().getColorStateList(R.color.black));


    }


    private void place_fragment(Fragment fragment,String flag){
        frameLayout.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.payment_form,fragment,flag);
        fragmentTransaction.commit();
    }


    public void add_name(String n){
        name=n;
        int position=viewPager.getCurrentItem();

        Credit_Card_Adapter creditCardAdapter=new Credit_Card_Adapter(getActivity(),number,name,mm,yy);
        viewPager.setAdapter(creditCardAdapter);
        viewPager.setCurrentItem(position);
    }

    public void add_number(String n){
        number=n;
        int position=viewPager.getCurrentItem();
        Credit_Card_Adapter creditCardAdapter=new Credit_Card_Adapter(getActivity(),number,name,mm,yy);
        viewPager.setAdapter(creditCardAdapter);
        viewPager.setCurrentItem(position);
    }

    public void add_mm(String n){
        mm=n;
        int position=viewPager.getCurrentItem();
        Credit_Card_Adapter creditCardAdapter=new Credit_Card_Adapter(getActivity(),number,name,mm,yy);
        viewPager.setAdapter(creditCardAdapter);
        viewPager.setCurrentItem(position);
    }

    public void add_yy(String n){
        yy=n;
        int position=viewPager.getCurrentItem();
        Credit_Card_Adapter creditCardAdapter=new Credit_Card_Adapter(getActivity(),number,name,mm,yy);
        viewPager.setAdapter(creditCardAdapter);
        viewPager.setCurrentItem(position);
    }


    public void add_cvv(String n) {

        cvv=n;
    }


    public String get_OrderMethod(){
        return orderMethod;
    }

    public boolean is_ok(String method){
        if(method.matches("Paypal")){

            if(getActivity().getSupportFragmentManager().findFragmentByTag("Paypal")!=null) {

                PayPal_payment payPalPayment=(PayPal_payment) getActivity().getSupportFragmentManager().findFragmentByTag("Paypal");
                if(payPalPayment.is_ok()){
                    paypal_Emailtext=payPalPayment.get_Paypal();
                    return true;
                }

            }
        }

        else if(method.matches("Credit")){

            if(name.trim().length()!=0&&number.trim().length()!=0&&yy.trim().length()!=0&&mm.trim().length()!=0&&cvv.trim().length()!=0){
                if(number.trim().length()==16&&cvv.trim().length()==3){
                    return true;
                }
            }

        }

        return false;
    }


    public Map<String,String> get_Field(String method){

        Map<String,String> field=new HashMap<>();
        if(method.matches("Paypal")){

            field.put("Paypal",paypal_Emailtext);
        }
        else if(method.matches("Credit")){
            field.put("Name",name);
            field.put("Number",number);
            field.put("Year",yy);
            field.put("Month",mm);
            field.put("Cvv",cvv);


        }

        return field;
    }




    public interface OnFragmentInteractionListener {

        void append_pram(String name,int i);





    }

}
