package yaseerfarah22.com.ozet_design.Model;

/**
 * Created by DELL on 12/27/2018.
 */

public class Customer {

    private String id;
    private String name;
    private String image_url;
    private String password;
    private String email;
    private int number;
    private String address;



    public Customer(){};


    public Customer(String id ,String name,String password,String email, int number,String address,String image_url){

        this.id=id;
        this.name=name;
        this.address=address;
        this.email=email;
        this.password=password;
        this.number=number;
        this.image_url=image_url;

    }


    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getImage_url() {
        return image_url;
    }
}
