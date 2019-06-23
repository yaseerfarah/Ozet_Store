package yaseerfarah22.com.ozet_design.Dagger2.Module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yaseerfarah22.com.ozet_design.View.Cart;
import yaseerfarah22.com.ozet_design.View.Product_desc;
import yaseerfarah22.com.ozet_design.View.fire;
import yaseerfarah22.com.ozet_design.View.home;

/**
 * Created by DELL on 5/18/2019.
 */
@Module
public abstract class MainActivityFragments {


    @ContributesAndroidInjector()
    abstract Cart contributeCartFragmet();

    @ContributesAndroidInjector()
    abstract home contributeHomeFragmet();

    @ContributesAndroidInjector()
    abstract fire contributeFireFragmet();

    @ContributesAndroidInjector()
    abstract Product_desc contributeProduct_descFragmet();




}
