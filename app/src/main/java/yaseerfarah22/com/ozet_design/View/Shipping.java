package yaseerfarah22.com.ozet_design.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import yaseerfarah22.com.ozet_design.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Shipping extends Fragment {


    EditText name,email,phone,city,address;
    public Shipping() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shipping, container, false);

        name=(EditText)view.findViewById(R.id.name_edit);
        email=(EditText)view.findViewById(R.id.email_edit);
        phone=(EditText)view.findViewById(R.id.phone_edit);
        city=(EditText)view.findViewById(R.id.city_edit);
        address=(EditText)view.findViewById(R.id.address_edit);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }



    public boolean is_ok(){

        if(name.getText().toString().trim().length()!=0&&email.getText().toString().trim().length()!=0&&phone.getText().toString().trim().length()!=0&&city.getText().toString().trim().length()!=0&&address.getText().toString().trim().length()!=0){
            if(email.getText().toString().contains("@")&&email.getText().toString().contains(".")){
                return true;
            }

        }

        return false;
    }



    public Map<String,String> get_Field(){
        Map<String,String > field=new HashMap<>();

        field.put("Name",name.getText().toString());
        field.put("Email",email.getText().toString());
        field.put("Phone",phone.getText().toString());
        field.put("City",city.getText().toString());
        field.put("Address",address.getText().toString());

        return field;

    }



}

