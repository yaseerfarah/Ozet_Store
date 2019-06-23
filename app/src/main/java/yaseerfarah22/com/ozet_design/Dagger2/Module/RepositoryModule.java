package yaseerfarah22.com.ozet_design.Dagger2.Module;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import yaseerfarah22.com.ozet_design.Repository.FirestoreMethod;
import yaseerfarah22.com.ozet_design.Repository.Room.ProductRoomDatabase;

/**
 * Created by DELL on 5/24/2019.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public FirestoreMethod firestoreMethod(){

       return new FirestoreMethod();
    }


    @Provides
    @Singleton
    public ProductRoomDatabase roomDatabase(Context context){
        return Room.databaseBuilder(context,ProductRoomDatabase.class,"Productdb").build();
    }



}
