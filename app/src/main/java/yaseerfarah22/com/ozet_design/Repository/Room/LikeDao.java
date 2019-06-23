package yaseerfarah22.com.ozet_design.Repository.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import yaseerfarah22.com.ozet_design.Model.Cart_info;
import yaseerfarah22.com.ozet_design.Model.Likes;
import yaseerfarah22.com.ozet_design.Model.RoomLike;

/**
 * Created by DELL on 5/23/2019.
 */
@Dao
public interface LikeDao {

    @Insert
    public void insertLike(RoomLike like);

    @Query("select * from Like")
    public List<RoomLike> getLikes();

    @Delete
    public void deleteLike(RoomLike like);


}
