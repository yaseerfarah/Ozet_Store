package yaseerfarah22.com.ozet_design.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.MyCallback;
import yaseerfarah22.com.ozet_design.Repository.FirestoreMethod;

/**
 * Created by DELL on 5/5/2019.
 */

public class OzetViewModel extends ViewModel  {


    private boolean isValid=false;
    private MutableLiveData<List<Product_info>> productLiveData=new MutableLiveData<>();
    private MutableLiveData<List<Product_info>> productNewLiveData=new MutableLiveData<>();
    private FirestoreMethod firestoreMethod;
    private Context context;


    @Inject
    public OzetViewModel(Context context,FirestoreMethod firestoreMethod){

        this.context=context;
        //Toast.makeText(context,"hi",Toast.LENGTH_LONG).show();

       // firestoreMethod=new FirestoreMethod();
        if (this.productLiveData.getValue()==null){
            isValid=false;
            firestoreMethod.getInfo("Products", Product_info.class, new MyCallback() {
                @Override
                public void onCallback(List list, String actionType) {
                    List<Product_info>product_infos=list;
                    productLiveData.postValue(product_infos);
                    isValid=true;
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }

        if (this.productNewLiveData.getValue()==null){
            isValid=false;
            firestoreMethod.getInfoOrderBy("Products","product_date", Product_info.class, new MyCallback() {
                @Override
                public void onCallback(List list, String actionType) {
                    List<Product_info>product_infos=list;
                    productNewLiveData.postValue(product_infos);
                    isValid=true;
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }

    }


    public LiveData getProductLiveData(){

        return productLiveData;
    }

    public LiveData getProductNewLiveData(){

        return productNewLiveData;
    }

    public boolean isValid(){
        return isValid;
    }






}
