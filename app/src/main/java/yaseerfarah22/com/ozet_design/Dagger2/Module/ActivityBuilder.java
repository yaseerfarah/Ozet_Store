package yaseerfarah22.com.ozet_design.Dagger2.Module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yaseerfarah22.com.ozet_design.View.CheckOut;
import yaseerfarah22.com.ozet_design.View.Fragment_holder;
import yaseerfarah22.com.ozet_design.View.MainActivity;
import yaseerfarah22.com.ozet_design.View.Order_;

/**
 * Created by DELL on 5/18/2019.
 */
@Module
public abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = MainActivityFragments.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentHolderFragments.class)
    abstract Fragment_holder contributeFragmentHolder();

    @ContributesAndroidInjector()
    abstract CheckOut contributeCheckOut();


    @ContributesAndroidInjector()
    abstract Order_ contributeOrder_();



}
