package yaseerfarah22.com.ozet_design.Model;

import java.util.List;

/**
 * Created by DELL on 2/27/2019.
 */

public class User_order {

    String id;
    List<String> order;

    public User_order(){}

    public User_order(String id, List<String> order) {
        this.id = id;
        this.order = order;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public List<String> getOrder() {
        return order;
    }
}
