package yaseerfarah22.com.ozet_design.Model;

import java.io.Serializable;

import yaseerfarah22.com.ozet_design.ViewModel.OzetViewModel;

/**
 * Created by DELL on 5/6/2019.
 */

public class ViewModelHelper implements Serializable {

    transient private OzetViewModel ozetViewModel;

    public ViewModelHelper(OzetViewModel ozetViewModel) {
        this.ozetViewModel = ozetViewModel;
    }

    public OzetViewModel getOzetViewModel() {
        return ozetViewModel;
    }

    public void setOzetViewModel(OzetViewModel ozetViewModel) {
        this.ozetViewModel = ozetViewModel;
    }
}
