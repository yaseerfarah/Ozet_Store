package yaseerfarah22.com.ozet_design;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by DELL on 1/17/2019.
 */

public class Product implements Serializable{
    String pro_image,name,price;
    transient Drawable  drawable=null;

    public Product(){};

    public Product(String pro_image, String name, String price) {
        this.pro_image = pro_image;
        this.name = name;
        this.price = price;
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public String getPro_image() {
        return pro_image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }


    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
