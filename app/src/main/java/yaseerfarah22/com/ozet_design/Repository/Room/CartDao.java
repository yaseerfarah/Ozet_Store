package yaseerfarah22.com.ozet_design.Repository.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import yaseerfarah22.com.ozet_design.Model.Cart_info;

/**
 * Created by DELL on 5/23/2019.
 */
@Dao
public interface CartDao {

    @Insert
    public void insertCart(Cart_info cart);

    @Query("select * from Carts")
    public List<Cart_info> getCarts();

    @Delete
    public void deleteCart(Cart_info cart);


}
