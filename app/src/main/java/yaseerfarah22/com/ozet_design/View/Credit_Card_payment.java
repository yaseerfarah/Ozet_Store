package yaseerfarah22.com.ozet_design.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import yaseerfarah22.com.ozet_design.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Credit_Card_payment extends Fragment {


    public Credit_Card_payment() {
        // Required empty public constructor
    }


    EditText number,name1,mm,yy,cvv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_credit__card_payment, container, false);
        final CheckOut checkOut=(CheckOut) getActivity();
        number=(EditText)v.findViewById(R.id.card_number_edit);
        name1=(EditText)v.findViewById(R.id.card_name_edit);
        mm=(EditText)v.findViewById(R.id.card_expirationmm_edit);
        yy=(EditText)v.findViewById(R.id.card_expirationyy_edit);
        cvv=(EditText)v.findViewById(R.id.card_cvv_edit);

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkOut.append_pram(charSequence.toString(),2);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        name1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkOut.append_pram(charSequence.toString(),1);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkOut.append_pram(charSequence.toString(),3);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        yy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkOut.append_pram(charSequence.toString(),4);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkOut.append_pram(charSequence.toString(),5);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return  v;
    }

}
