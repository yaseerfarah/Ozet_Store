package yaseerfarah22.com.ozet_design;

import java.util.List;

/**
 * Created by DELL on 5/5/2019.
 */

public interface MyCallback {

    void onCallback(List list,String actionType);
    void onFailure(String e);
}
