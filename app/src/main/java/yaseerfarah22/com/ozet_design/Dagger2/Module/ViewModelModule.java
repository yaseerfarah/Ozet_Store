package yaseerfarah22.com.ozet_design.Dagger2.Module;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.*;
import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import yaseerfarah22.com.ozet_design.Repository.FirestoreMethod;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.UserCollectionViewModel;
import yaseerfarah22.com.ozet_design.ViewModel.ViewModelFactory;

/**
 * Created by DELL on 5/16/2019.
 */
@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }





    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @Singleton
    @IntoMap
    @ViewModelKey(OzetViewModel.class)
    ViewModel ozetViewModel(Context context, FirestoreMethod firestoreMethod) {
        return new OzetViewModel(context,firestoreMethod);
    }


    @Provides
    @Singleton
    @IntoMap
    @ViewModelKey(UserCollectionViewModel.class)
    ViewModel userCollectoinViewModel(Context context, FirestoreMethod firestoreMethod) {
        return new UserCollectionViewModel(context,firestoreMethod);
    }

}
