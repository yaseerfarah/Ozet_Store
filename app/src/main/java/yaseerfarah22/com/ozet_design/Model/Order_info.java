package yaseerfarah22.com.ozet_design.Model;

/**
 * Created by DELL on 2/27/2019.
 */

public class Order_info {

    String user_id,order_id,order_status,order_method;
    Cart_info cart_info;
    String order_date;
    User_info user_info;



    public Order_info(){}

    public Order_info(String user_id,String order_id, String order_status, String order_method, Cart_info cart_info, String order_date,User_info user_info) {
        this.user_id = user_id;
        this.order_id=order_id;
        this.order_status = order_status;
        this.order_method = order_method;
       this.cart_info=cart_info;
        this.order_date = order_date;
        this.user_info=user_info;

    }


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setCart_info(Cart_info cart_info) {
        this.cart_info = cart_info;
    }

    public User_info getUser_info() {
        return user_info;
    }

    public void setUser_info(User_info user_info) {
        this.user_info = user_info;
    }

    public void setUser_id(String id) {
        this.user_id = id;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setOrder_method(String order_method) {
        this.order_method = order_method;
    }

    public void setCard(Cart_info cart_info) {
        this.cart_info = cart_info;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }



    public String getUser_id() {
        return user_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_method() {
        return order_method;
    }

    public Cart_info getCart_info() {
        return cart_info;
    }

    public String getOrder_date() {
        return order_date;
    }

}
