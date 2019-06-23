package yaseerfarah22.com.ozet_design.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

/**
 * Created by DELL on 5/24/2019.
 */
@Entity(tableName = "Like")
public class RoomLike {




    @PrimaryKey
    @NonNull
    String userId;

    String likeId;

    public RoomLike(String userId, String likeId) {

        this.userId = userId;
        this.likeId = likeId;
    }




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }
}
