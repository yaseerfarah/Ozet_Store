package yaseerfarah22.com.ozet_design.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by DELL on 5/16/2019.
 */

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<?extends ViewModel>,Provider<ViewModel>> classProviderMap;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> classProviderMap) {
        this.classProviderMap = classProviderMap;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


        return (T)classProviderMap.get(modelClass).get();
    }
}
