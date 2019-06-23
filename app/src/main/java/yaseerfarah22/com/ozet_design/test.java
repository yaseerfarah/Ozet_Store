package yaseerfarah22.com.ozet_design;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import yaseerfarah22.com.ozet_design.Adapter.Slider_Adapter;

public class test extends AppCompatActivity {

    ViewPager viewPager;
    EditText title,price,size1,size2,size3,size4,size5;
    Spinner category,sizes;
    Slider_Adapter sliderAdapter;
    List<EditText> size_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ozet);

        viewPager=(ViewPager)findViewById(R.id.a_view_pager);
        title=(EditText) findViewById(R.id.atitle);
        price=(EditText) findViewById(R.id.aprice);
        size1=(EditText) findViewById(R.id.size1);
        size2=(EditText) findViewById(R.id.size2);
        size3=(EditText) findViewById(R.id.size3);
        size4=(EditText) findViewById(R.id.size4);
        size5=(EditText) findViewById(R.id.size5);
        category=(Spinner)findViewById(R.id.add_spiner);
        sizes=(Spinner)findViewById(R.id.add_sizespinner);
        size_text=new ArrayList<>();
        size_text.add(size1);
        size_text.add(size2);
        size_text.add(size3);
        size_text.add(size4);
        size_text.add(size5);

        String[] category_text={"Clothes","Shoes","Pants"};
        String[] sizes_text={"1","2","3","4","5"};
        ArrayAdapter category_adapter=new ArrayAdapter(test.this, R.layout.support_simple_spinner_dropdown_item,category_text);
        ArrayAdapter sizes_adapter=new ArrayAdapter(test.this, R.layout.support_simple_spinner_dropdown_item,sizes_text);
        category.setAdapter(category_adapter);
        sizes.setAdapter(sizes_adapter);
        sizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                size_enable(Integer.valueOf(sizes.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        




    }


    private void size_enable(int number){

        for (int i=0;i<size_text.size();i++){

            size_text.get(i).setVisibility(View.INVISIBLE);
        }

        for (int i=0;i<number;i++){

            size_text.get(i).setVisibility(View.VISIBLE);
        }

    }

}
