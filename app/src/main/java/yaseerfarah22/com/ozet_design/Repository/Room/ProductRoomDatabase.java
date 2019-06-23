package yaseerfarah22.com.ozet_design.Repository.Room;

import android.arch.persistence.room.Database;

import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.RoomLike;

/**
 * Created by DELL on 5/23/2019.
 */
@Database(entities = {RoomLike.class, Cart_info.class},version = 1)
public abstract class ProductRoomDatabase extends android.arch.persistence.room.RoomDatabase {


    public abstract CartDao cartDao();

    public abstract LikeDao likeDao();

}
