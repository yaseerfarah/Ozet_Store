package yaseerfarah22.com.ozet_design.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import yaseerfarah22.com.ozet_design.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PayPal_payment extends Fragment {


    EditText paypal;

    public PayPal_payment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pay_pal_payment, container, false);

        paypal=(EditText)view.findViewById(R.id.paypal_edit);

        return view;
    }



    public boolean is_ok(){
        if(paypal.getText().toString().trim().length()!=0&&paypal.getText().toString().contains("@")&&paypal.getText().toString().contains(".")){

            return true;
        }
        return false;
    }

    public String get_Paypal(){

        return paypal.getText().toString();
    }

}
