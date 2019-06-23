package yaseerfarah22.com.ozet_design.ViewModel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.DocumentSnapListener;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.MyCallback;
import yaseerfarah22.com.ozet_design.MyListener;
import yaseerfarah22.com.ozet_design.Repository.FirestoreMethod;
import yaseerfarah22.com.ozet_design.Repository.ListenFirestoreItem;

/**
 * Created by DELL on 5/5/2019.
 */

public class UserCollectionViewModel extends ViewModel  {



    private MediatorLiveData<List<Cart_info>> cartLiveData=new MediatorLiveData<>();
    private List<Cart_info>cart_infos=new ArrayList<>();
    private List<Likes>likesList=new ArrayList<>();
    private MediatorLiveData<List<Likes>> likeLiveData=new MediatorLiveData<>();
    private FirestoreMethod firestoreMethod;
    private Context context;
    private  ListenFirestoreItem cartListen;


    @Inject
    public UserCollectionViewModel(Context context, FirestoreMethod firestoreMethod){

        this.context=context;
        this.firestoreMethod=firestoreMethod;


       // firestoreMethod=new FirestoreMethod();
        if (this.cartLiveData.getValue()==null){
           //setCartLiveData();
            cartAddSource();
        }

        if (this.likeLiveData.getValue()==null){
            setLikeLiveData();
            //likeAddSource();
        }


       // cartListen=new ListenFirestoreItem("Cart");

        //cartLiveData=(MediatorLiveData<List<Cart_info>>) Transformations.switchMap(cartListen,new Deserializer());






    }


    public LiveData getCartLiveData(){

        return cartLiveData;
    }






    public LiveData getLikeLiveData(){

        return likeLiveData;
    }






    private void setCartLiveData(){
        firestoreMethod.getInfo("Cart", Cart_info.class, new MyCallback() {
            @Override
            public void onCallback(List list, String actionType) {
                List<Cart_info>cart_info=list;
                cartLiveData.postValue(cart_info);

            }

            @Override
            public void onFailure(String e) {

            }
        });
    }







    private void setLikeLiveData(){

        firestoreMethod.getInfo("Likes", Product_info.class, new MyCallback() {
            @Override
            public void onCallback(List list, String actionType) {
                List<Likes>likesList=list;
                likeLiveData.postValue(likesList);

            }

            @Override
            public void onFailure(String e) {

            }
        });
    }






    private void cartAddSource(){

        cartLiveData.addSource(new ListenFirestoreItem("Cart"), new Observer<List<DocumentSnapListener>>() {
            @Override
            public void onChanged(@Nullable List<DocumentSnapListener> documentSnapListeners) {
                if (documentSnapListeners.size()>0) {

                    for (DocumentSnapListener documentSnapListener:documentSnapListeners) {

                        switch (documentSnapListener.getActionType()) {

                            case ListenFirestoreItem.addedTag:

                                Cart_info cart_info = documentSnapListener.getDocumentSnapshot().toObject(Cart_info.class) != null ? documentSnapListener.getDocumentSnapshot().toObject(Cart_info.class) : new Cart_info();
                                cart_infos.add(cart_info);
                                break;

                           /* case ListenFirestoreItem.removedTag:

                                for (int i = 0; i < cart_infos.size(); i++) {
                                    if (cart_infos.get(i).getCartId() == documentSnapListener.getDocumentSnapshot().toObject(Cart_info.class).getCartId()) {
                                        cart_infos.remove(i);
                                        break;
                                    }
                                }

                                break;*/

                        }
                    }

                    cartLiveData.postValue(cart_infos);

                }
            }
        });

    }






    private void likeAddSource(){

        likeLiveData.addSource(new ListenFirestoreItem("Likes"), new Observer<List<DocumentSnapListener>>() {
            @Override
            public void onChanged(@Nullable List<DocumentSnapListener> documentSnapListeners) {
                if (documentSnapListeners.size()>0) {

                    for (DocumentSnapListener documentSnapListener:documentSnapListeners) {

                        switch (documentSnapListener.getActionType()) {

                            case ListenFirestoreItem.addedTag:

                                Likes likes = documentSnapListener.getDocumentSnapshot().toObject(Likes.class) != null ? documentSnapListener.getDocumentSnapshot().toObject(Likes.class) : new Likes();
                                likesList.add(likes);
                                break;

                           /* case ListenFirestoreItem.removedTag:

                                for (int i = 0; i < cart_infos.size(); i++) {
                                    if (cart_infos.get(i).getCartId() == documentSnapListener.getDocumentSnapshot().toObject(Cart_info.class).getCartId()) {
                                        cart_infos.remove(i);
                                        break;
                                    }
                                }

                                break;*/

                        }
                    }

                    likeLiveData.postValue(likesList);

                }
            }
        });

    }





    public void deleteCart(final Cart_info cart_info){

        firestoreMethod.deleteFromDatabase("Cart", cart_info.getCartId(), new MyListener() {
            @Override
            public void onSuccess() {
                for (int i = 0; i < cart_infos.size(); i++) {
                    if (cart_infos.get(i).getCartId() == cart_info.getCartId()) {
                        cart_infos.remove(i);
                        break;
                    }
                }
                cartLiveData.postValue(cart_infos);
            }

            @Override
            public void onFailure(String e) {
                Toast.makeText(context,"error del cart",Toast.LENGTH_LONG).show();

            }
        });

    }





    public void deleteLike(final Likes likes){

        firestoreMethod.deleteFromDatabase("Likes", likes.getLikeId(), new MyListener() {
            @Override
            public void onSuccess() {
                for (int i = 0; i < likesList.size(); i++) {
                    if (likesList.get(i).getLikeId() == likes.getLikeId()) {
                        likesList.remove(i);
                        break;
                    }
                }
                likeLiveData.postValue(likesList);
            }

            @Override
            public void onFailure(String e) {
                Toast.makeText(context,"error del like",Toast.LENGTH_LONG).show();

            }
        });

    }



}
