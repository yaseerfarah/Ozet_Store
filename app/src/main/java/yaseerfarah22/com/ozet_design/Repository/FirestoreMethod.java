package yaseerfarah22.com.ozet_design.Repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Product_info;
import yaseerfarah22.com.ozet_design.MyCallback;
import yaseerfarah22.com.ozet_design.MyListener;

/**
 * Created by DELL on 5/5/2019.
 */

public class FirestoreMethod {


    private FirebaseFirestore firebaseFirestore;


    public FirestoreMethod() {

        this.firebaseFirestore=FirebaseFirestore.getInstance();

    }

    public void getInfoEqualTo(String collectionRef, String columnName, String attributeName, final Class className, final MyCallback myCallback){


        CollectionReference collectionReference=firebaseFirestore.collection(collectionRef);
        final List list=new ArrayList();

        collectionReference.whereEqualTo(columnName,attributeName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){

                    list.add(documentSnapshot.toObject(className));
                    myCallback.onCallback(list,"Get");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myCallback.onFailure(e.getMessage().toString());
                return ;
            }
        });

    }


    public void getInfo(String collectionRef,final Class className, final MyCallback myCallback){
        CollectionReference collectionReference=firebaseFirestore.collection(collectionRef);
        final List list=new ArrayList();

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){

                    list.add(documentSnapshot.toObject(className));
                    myCallback.onCallback(list,"Get");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myCallback.onFailure(e.getMessage().toString());
                return ;
            }
        });

    }


    public void getInfoOrderBy(String collectionRef, String columnName, final Class className, final MyCallback myCallback){

        CollectionReference collectionReference=firebaseFirestore.collection(collectionRef);
        final List list=new ArrayList();

        collectionReference.orderBy(columnName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){

                    list.add(documentSnapshot.toObject(className));
                    myCallback.onCallback(list,"Get");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myCallback.onFailure(e.getMessage().toString());
                return ;
            }
        });
    }


    public String getRandomID(String collectionRef){

        CollectionReference collectionReference=firebaseFirestore.collection(collectionRef);
        return collectionReference.document().getId().toString();
    }


    public void addToDatabase(String collectionRef, Object object, String id, final MyListener myListener){
        CollectionReference collectionReference=firebaseFirestore.collection(collectionRef);

        collectionReference.document(id).set(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                myListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myListener.onFailure(e.getMessage().toString());
            }
        });

    }


    public void addToDatabase(String collectionRef, Object object,final MyListener myListener){
        CollectionReference collectionReference=firebaseFirestore.collection(collectionRef);

        collectionReference.add(object).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                myListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myListener.onFailure(e.getMessage().toString());
            }
        });

    }


    public void deleteFromDatabase(String collectionRef, String id, final MyListener myListener){
        CollectionReference collectionReference=firebaseFirestore.collection(collectionRef);

        collectionReference.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                myListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myListener.onFailure(e.getMessage().toString());
            }
        });

    }




}
