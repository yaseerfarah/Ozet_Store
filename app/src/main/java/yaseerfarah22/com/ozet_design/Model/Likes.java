package yaseerfarah22.com.ozet_design.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by DELL on 4/18/2019.
 */

public class Likes implements Serializable {


    String userId;

    String likeId;
    Product_info product_info;

    public Likes(){

    }

    public Likes(String likeId, String userId, Product_info product_info) {
        this.likeId=likeId;
        this.userId = userId;
        this.product_info = product_info;
    }


    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Product_info getProduct_info() {
        return product_info;
    }

    public void setProduct_info(Product_info product_info) {
        this.product_info = product_info;
    }
}
