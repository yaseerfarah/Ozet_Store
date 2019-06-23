package yaseerfarah22.com.ozet_design.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import yaseerfarah22.com.ozet_design.Model.User_info;
import yaseerfarah22.com.ozet_design.R;

public class Login extends AppCompatActivity {

    ImageButton loginButton,google_login;
    CallbackManager callbackManager;
    private TextView displayName, emailID;
    private ImageView displayImage;
    AccessTokenTracker accessTokenTracker;
    GoogleSignInClient googleSignInClient;

    FirebaseFirestore db;
    CollectionReference user_R;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db=FirebaseFirestore.getInstance();
        user_R=db.collection("User");

        displayName = findViewById(R.id.display_name);
        emailID = findViewById(R.id.email);
        displayImage = findViewById(R.id.image_view);
        loginButton=(ImageButton) findViewById(R.id.facebook_login);
        google_login=(ImageButton) findViewById(R.id.google_login);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 102);
            }
        });

        // Creating CallbackManager
        callbackManager = CallbackManager.Factory.create();
       /* LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Retrieving access token using the LoginResult
                AccessToken accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
                flag=1;
                loginButton.setText("LogOut");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });*/
       // loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                   // useLoginInformation(currentAccessToken);
                    useLoginInformation(currentAccessToken);

                }
            }
        };




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    LoginManager.getInstance().logInWithReadPermissions(Login.this,Arrays.asList("email", "public_profile"));

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        accessTokenTracker.startTracking();
        if(accessToken!=null){

           // useLoginInformation(accessToken);
            Intent intent=new Intent(Login.this,MainActivity.class);
            intent.putExtra("Flag","Facebook");
            intent.putExtra("Facebook_L",accessToken);

            startActivity(intent);
            finish();

        }


        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Intent intent=new Intent(Login.this,MainActivity.class);
            intent.putExtra("Flag","Google");
            intent.putExtra("Google_L",account);
            startActivity(intent);
            finish();
        }


    }


    private void clearAll(){

        displayImage.setImageDrawable(null);
        emailID.setText("");
        displayName.setText("");
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    private void useLoginInformation(final AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/

        final List<String> parameters=new ArrayList<>();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {

                    parameters.add(object.getString("name"));
                    parameters.add(object.getString("email"));
                    parameters.add(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                    user_check_facebook(parameters,accessToken);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(bundle);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resulrCode, Intent data) {
        super.onActivityResult(requestCode, resulrCode, data);

        if(resulrCode==RESULT_OK){


                callbackManager.onActivityResult(requestCode,resulrCode,data);



            if(requestCode==102){
                //google sign in

                try {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);

                user_check_google(account);

            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                Log.w("google sign in error", "signInResult:failed code=" + e.getStatusCode());
            }

            }
        }
    }



    private void user_check_google(final GoogleSignInAccount account){

        user_R.whereEqualTo("user_id",account.getEmail().trim().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    String[] name=account.getDisplayName().split(" ");


                    User_info user_info=new User_info(account.getEmail().trim(),name[0],name[name.length-1],account.getEmail().trim(),account.getPhotoUrl().toString(),"","","",null);

                    user_R.document(user_info.getUser_id()).set(user_info).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            intent.putExtra("Flag","Google");
                            intent.putExtra("Google_L",account);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
                else {
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    intent.putExtra("Flag","Google");
                    intent.putExtra("Google_L",account);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }


    private void user_check_facebook(final List<String> parameters, final AccessToken accessToken){

        user_R.whereEqualTo("user_id",parameters.get(1).trim()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(queryDocumentSnapshots.isEmpty()){

                    String[] name=parameters.get(0).split(" ");
                    User_info user_info=new User_info(parameters.get(1).trim(),name[0],name[name.length-1],parameters.get(1).trim(),parameters.get(2).trim(),"","","",null);


                    user_R.document(user_info.getUser_id()).set(user_info).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            intent.putExtra("Flag","Facebook");
                            intent.putExtra("Facebook_L",accessToken);

                            startActivity(intent);
                            finish();
                        }
                    });


                }
                else {
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    intent.putExtra("Flag","Facebook");
                    intent.putExtra("Facebook_L",accessToken);

                    startActivity(intent);
                    finish();
                }


            }
        });



    }


}
