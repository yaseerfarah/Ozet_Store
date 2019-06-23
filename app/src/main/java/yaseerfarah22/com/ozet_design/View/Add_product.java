package yaseerfarah22.com.ozet_design.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.R;
import yaseerfarah22.com.ozet_design.Adapter.Slider_Adapter;

public class Add_product extends AppCompatActivity {

    ViewPager viewPager;
    EditText title,price,stock,description,size1,size2,size3,size4,size5;
    Spinner category,sizes;
    Slider_Adapter sliderAdapter;
    List<EditText> size_text;
    ImageButton add_image;
    ImageButton done;


    private static final int PICK_FROM_GALLARY = 2;

    private List<Uri> pro_image;
    private List<String> pro_im_uri;
    private List<Bitmap> images;
    private List<String> images_place;
    private List<String> size_text_field;
    Product_info pro;
    ProgressBar progressBar;


    ///flags///
    private boolean is_pick=false;



    ///Firebase///
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ozet);



        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference().child("Product");
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("Product_image");

        size_text_field=new ArrayList<>();
        images_place=new ArrayList<>();
        pro_im_uri=new ArrayList<>();
        pro_image=new ArrayList<>();
        images=new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.done_progress);
        sliderAdapter=new Slider_Adapter(Add_product.this,images);
        viewPager=(ViewPager)findViewById(R.id.a_view_pager);
        title=(EditText) findViewById(R.id.atitle);
        price=(EditText) findViewById(R.id.aprice);
        size1=(EditText) findViewById(R.id.size1);
        size2=(EditText) findViewById(R.id.size2);
        size3=(EditText) findViewById(R.id.size3);
        size4=(EditText) findViewById(R.id.size4);
        size5=(EditText) findViewById(R.id.size5);
        stock=(EditText) findViewById(R.id.astock);
        description=(EditText) findViewById(R.id.description_field);
        add_image=(ImageButton)findViewById(R.id.a_addimage);
        done=(ImageButton)findViewById(R.id.add_done);
        category=(Spinner)findViewById(R.id.add_spiner);
        sizes=(Spinner)findViewById(R.id.add_sizespinner);
        size_text=new ArrayList<>();
        size_text.add(size1);
        size_text.add(size2);
        size_text.add(size3);
        size_text.add(size4);
        size_text.add(size5);
        progressBar.setVisibility(View.INVISIBLE);

        viewPager.setAdapter(sliderAdapter);
        String[] category_text={"Clothes","Shoes","Pants"};
        String[] sizes_text={"1","2","3","4","5"};
        ArrayAdapter category_adapter=new ArrayAdapter(Add_product.this, R.layout.support_simple_spinner_dropdown_item,category_text);
        ArrayAdapter sizes_adapter=new ArrayAdapter(Add_product.this, R.layout.support_simple_spinner_dropdown_item,sizes_text);
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





        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(gallery,PICK_FROM_GALLARY);
            }
        });



/////////////////////////////////////////////////////////////// Done Button//////////////////////////////////////////////////////////////


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().trim().length()!=0&&description.getText().toString().trim().length()!=0 &&stock.getText().toString().trim().length()!=0&&price.getText().toString().trim().length()!=0&&is_pick&&is_size_field_good(Integer.valueOf(sizes.getSelectedItem().toString()))){
                    final String id=databaseReference.push().getKey();

                    progressBar.setVisibility(View.VISIBLE);
                    ////////////size Field//////////////////////

                    get_text_sizes(Integer.valueOf(sizes.getSelectedItem().toString()));




                    done.setVisibility(View.INVISIBLE);

                    ////////////////// Storage//////////////////////////

                    for (int i=0;i<pro_image.size();i++){

                        images_place.add(pro_image.get(i).getLastPathSegment());

                        final StorageReference Ref_image=storageReference.child(id).child(String.valueOf(images_place.get(i)));
                        UploadTask uploadTask= Ref_image.putFile(pro_image.get(i));
                        final int check=i;
                        Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Add_product.this,"Wrong1",Toast.LENGTH_SHORT).show();

                                    throw task.getException();
                                }


                                // Continue with the task to get the download URL
                                return Ref_image.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    pro_im_uri.add(String.valueOf(downloadUri));
                                    Toast.makeText(Add_product.this,String.valueOf(check),Toast.LENGTH_SHORT).show();




                                }
                            }
                        });


                    }

                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true){
                                if(pro_im_uri.size()==pro_image.size()){
                                    add_to_database(id);
                                    break;
                                }
                            }
                        }
                    });


                    thread.start();




                }
                else {
                    Toast.makeText(Add_product.this,"Wrong",Toast.LENGTH_SHORT).show();
                }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_FROM_GALLARY){
            if(resultCode==RESULT_OK) {
                pro_image.add( data.getData());
                try {
                    InputStream inputStream = getContentResolver().openInputStream(pro_image.get(pro_image.size()-1));
                    images.add(BitmapFactory.decodeStream(inputStream));
                    sliderAdapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                is_pick = true;
            }
        }

    }



    private void add_to_database(String id){

        ///Realtime Database////


      //  pro = new Product_info(id,title.getText().toString(),category.getSelectedItem().toString(),pro_im_uri,price.getText().toString(),"0",String.valueOf(Calendar.getInstance().getTime()),stock.getText().toString(),size_text_field,description.getText().toString(),images_place);
        databaseReference.child(id).setValue(pro);

        finish();

    }



    private void get_text_sizes(int fields){

        for (int i=0;i<fields;i++){
            size_text_field.add(size_text.get(i).getText().toString());
        }


    }


    private boolean is_size_field_good(int field){

        for (int i=0;i<field;i++){

            if(size_text.get(i).getText().toString().trim().length()==0){

                return false;
            }

        }

        return true;
    }


}
