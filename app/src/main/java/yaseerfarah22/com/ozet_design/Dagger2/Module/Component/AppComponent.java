package yaseerfarah22.com.ozet_design.Dagger2.Module.Component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import yaseerfarah22.com.ozet_design.Dagger2.Module.ActivityBuilder;
import yaseerfarah22.com.ozet_design.Dagger2.Module.RepositoryModule;
import yaseerfarah22.com.ozet_design.Dagger2.Module.ViewModelModule;
import yaseerfarah22.com.ozet_design.View.AppController;
import yaseerfarah22.com.ozet_design.View.MainActivity;
import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;

/**
 * Created by DELL on 5/16/2019.
 */
@Singleton
@Component(modules = {ViewModelModule.class,ActivityBuilder.class, AndroidSupportInjectionModule.class, RepositoryModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder appContext(Context context);

        AppComponent build();

    }



    void inject(AppController appController);

}
