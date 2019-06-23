package yaseerfarah22.com.ozet_design;

import java.util.List;

import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.Order_info;
import yaseerfarah22.com.ozet_design.Model.Product_info;

/**
 * Created by DELL on 4/30/2019.
 */

public interface DatabaseMethod {

    void add_like(Product_info product_info);
    void retrieve_likes();
    void delete_like(Likes likes);
    void add_to_cart(String size,String quantity ,Product_info product_info);
    void retrieve_carts();
    void delete_cart(Cart_info cart_info);
    void delete_AllCarts();
    void add_order( List<Cart_info> cartInfoList,  String order_st,  String order_md,  String order_dt);
    void retrieve_order();
    void delete_order( Order_info order_info);



}
