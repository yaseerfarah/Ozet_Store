package yaseerfarah22.com.ozet_design.View;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class Add_post extends AppCompatActivity {

    private ImageView upload;
    private EditText pro_name;
    private EditText pro_price;
    private Button done;
    private ProgressBar progressBar;
    private Product_info pro;

    private Uri pro_image=null;
    private String pro_im_uri;


    ///flags///
    private boolean is_pick=false;


    ///Constant///

    private static final int PICK_FROM_GALLARY = 2;

    ///Firebase///
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference().child("Product");
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("Product_image");



        upload=(ImageView) findViewById(R.id.image_b);
        pro_name=(EditText)findViewById(R.id.pro_name);
        pro_price=(EditText)findViewById(R.id.pro_pric);
        done=(Button)findViewById(R.id.done);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(gallery,PICK_FROM_GALLARY);
            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pro_name.getText().toString().trim().length()!=0||pro_price.getText().toString().trim().length()!=0||is_pick){
                    final String id=databaseReference.push().getKey();

                    progressBar.setVisibility(View.VISIBLE);
                    done.setVisibility(View.INVISIBLE);
                    /// Storage///

                    final StorageReference Ref_image=storageReference.child(id).child(String.valueOf(pro_image.getLastPathSegment()));
                    UploadTask uploadTask= Ref_image.putFile(pro_image);

                    Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Add_post.this,"Wrong1",Toast.LENGTH_SHORT).show();

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
                                pro_im_uri = String.valueOf(downloadUri);

                                ///Realtime Database////
                                List<String> im_s=new ArrayList<>();
                                im_s.add(pro_im_uri);
                              //  pro = new Product_info(id,pro_name.getText().toString(),"Clothes",im_s,pro_price.getText().toString(),"0",String.valueOf(Calendar.getInstance().getTime()),5);
                               // databaseReference.child(id).setValue(pro);

                                finish();
                            }
                        }
                    });




                }
                else {
                    Toast.makeText(Add_post.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_FROM_GALLARY){
            pro_image=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(pro_image);
                upload.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            is_pick=true;
        }

    }
}
