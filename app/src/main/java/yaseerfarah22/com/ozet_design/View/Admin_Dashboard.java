package yaseerfarah22.com.ozet_design.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import yaseerfarah22.com.ozet_design.R;

public class Admin_Dashboard extends AppCompatActivity {

    CardView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__dashboard);


        add=(CardView)findViewById(R.id.dash_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Dashboard.this,Add_product.class));
            }
        });


    }
}
