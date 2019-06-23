package yaseerfarah22.com.ozet_design.View;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import yaseerfarah22.com.ozet_design.Dagger2.Module.Component.DaggerAppComponent;


/**
 * Created by DELL on 5/18/2019.
 */

public class AppController extends Application implements HasActivityInjector
{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .appContext(getApplicationContext())
                .build()
                .inject(this);




    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
