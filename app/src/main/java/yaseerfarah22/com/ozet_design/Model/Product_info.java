package yaseerfarah22.com.ozet_design.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2/27/2019.
 */

public class Product_info implements Serializable {

    String id,name,category;
    List<String> images_url;
    String price,ex_price;
    String product_date;



    String offers;
    String  stock,purchase;
    List<String> sizes;
    String description;
    List<String> images_place;


    public Product_info(){}



    public Product_info(String id, String name, String category, List<String> images_url, String price, String ex_price, String product_date, String stock, List<String> sizes, String description, List<String> images_place,String offers,String purchase) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.images_url = images_url;
        this.price = price;
        this.ex_price = ex_price;
        this.product_date = product_date;
        this.stock = stock;
        this.sizes = sizes;
        this.description = description;
        this.images_place = images_place;
        this.offers=offers;
        this.purchase=purchase;
    }


    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages_place(List<String> images_place) {
        this.images_place = images_place;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImages_url(List<String> images_url) {
        this.images_url = images_url;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setEx_price(String ex_price) {
        this.ex_price = ex_price;
    }

    public void setProduct_date(String product_date) {
        this.product_date = product_date;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }


    public List<String> getSizes() {
        return sizes;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages_place() {
        return images_place;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getImages_url() {
        return images_url;
    }

    public String  getPrice() {
        return price;
    }

    public String getEx_price() {
        return ex_price;
    }

    public String getProduct_date() {
        return product_date;
    }

    public String getStock() {
        return stock;
    }
    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }
}
