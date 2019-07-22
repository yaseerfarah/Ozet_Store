package yaseerfarah22.com.ozet_design.Repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.Handler;
import android.os.ParcelUuid;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import yaseerfarah22.com.ozet_design.Model.DocumentSnapListener;
import yaseerfarah22.com.ozet_design.MyCallback;

/**
 * Created by DELL on 5/5/2019.
 */

public class ListenFirestoreItem extends LiveData<List<DocumentSnapListener>> {

    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    ListenerRegistration listenerRegistration=null;
    MyEvent myEvent=new MyEvent();

    public static final int addedTag=1;
    public static final int modifiedTag=2;
    public static final int removedTag=3;

    private boolean is_ListenerRemove = true;
    private final Handler handler = new Handler();
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            listenerRegistration.remove();
            is_ListenerRemove = true;
        }
    };

    Context context;
    public ListenFirestoreItem(String collectionRef, Context context) {
        this.firebaseFirestore=FirebaseFirestore.getInstance();
        this.collectionReference=firebaseFirestore.collection(collectionRef);
        this.context=context;
    }

    @Override
    protected void onActive() {

        if(is_ListenerRemove){
            listenerRegistration=this.collectionReference.addSnapshotListener(myEvent);
        }
        else {
            handler.removeCallbacks(removeListener);
        }

        is_ListenerRemove=false;

       // Toast.makeText(context,"active",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onInactive() {

        //Toast.makeText(context,"no",Toast.LENGTH_SHORT).show();
       // listenerRegistration.remove();

        handler.postDelayed(removeListener,5000);


    }



    private class MyEvent implements EventListener<QuerySnapshot>{


        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

            List<DocumentSnapListener>documentSnapListeners=new ArrayList<>();
            for (DocumentChange documentChange:queryDocumentSnapshots.getDocumentChanges()){
                switch (documentChange.getType()){

                    case ADDED:
                        documentSnapListeners.add(new DocumentSnapListener(documentChange.getDocument(),addedTag));
                        break;
                    case MODIFIED:
                        documentSnapListeners.add(new DocumentSnapListener(documentChange.getDocument(),modifiedTag));
                        break;

                    case REMOVED:
                        documentSnapListeners.add(new DocumentSnapListener(documentChange.getDocument(),removedTag));
                        break;

                }

            }

           setValue(documentSnapListeners);
        }
    }

}
