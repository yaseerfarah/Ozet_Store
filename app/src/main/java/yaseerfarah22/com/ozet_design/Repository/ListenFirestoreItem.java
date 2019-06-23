package yaseerfarah22.com.ozet_design.Repository;

import android.arch.lifecycle.LiveData;
import android.os.ParcelUuid;

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

    public ListenFirestoreItem(String collectionRef) {
        this.firebaseFirestore=FirebaseFirestore.getInstance();
        this.collectionReference=firebaseFirestore.collection(collectionRef);
    }

    @Override
    protected void onActive() {


        listenerRegistration=this.collectionReference.addSnapshotListener(myEvent);
    }

    @Override
    protected void onInactive() {

        listenerRegistration.remove();


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
