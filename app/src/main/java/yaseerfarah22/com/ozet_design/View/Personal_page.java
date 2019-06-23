package yaseerfarah22.com.ozet_design.View;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.Serializable;

import yaseerfarah22.com.ozet_design.R;


public class Personal_page extends AppCompatActivity  {

BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
/*
        final fire fire_=new fire();
        set_fragment(fire_);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.preson_navigation);

*/

    }




    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag("Person")!=null&&getSupportFragmentManager().findFragmentByTag("Person").isVisible()){
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(getSupportFragmentManager().findFragmentByTag("Person"));
            fragmentTransaction.commit();
            bottomNavigationView.setVisibility(View.VISIBLE);


        }

        else {
            super.onBackPressed();
        }
    }








    private void set_fragment(android.support.v4.app.Fragment fragment){

        android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.person_frame,fragment);
        fragmentTransaction.commit();
    }




}
