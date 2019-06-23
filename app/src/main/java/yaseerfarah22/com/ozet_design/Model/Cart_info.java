package yaseerfarah22.com.ozet_design.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by DELL on 2/27/2019.
 */
@Entity(tableName = "Carts")
public class Cart_info {


    @PrimaryKey
    @NonNull
    String cartId;
    String userId,product_id,size,quantity,pro_id,pro_name,pro_category,pro_price,pro_imageurl;

    public Cart_info() {
    }

    public Cart_info(String cartId,String userId,String product_id,String size, String quantity, String pro_id, String pro_name, String pro_category, String pro_price, String pro_imageurl) {
        this.cartId=cartId;
        this.userId=userId;
        this.product_id=product_id;
        this.size = size;
        this.quantity = quantity;
        this.pro_id = pro_id;
        this.pro_name = pro_name;
        this.pro_category = pro_category;
        this.pro_price = pro_price;
        this.pro_imageurl = pro_imageurl;
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_category() {
        return pro_category;
    }

    public void setPro_category(String pro_category) {
        this.pro_category = pro_category;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getPro_imageurl() {
        return pro_imageurl;
    }

    public void setPro_imageurl(String pro_imageurl) {
        this.pro_imageurl = pro_imageurl;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }




    public String getSize() {
        return size;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPro_id() {
        return pro_id;
    }
}
