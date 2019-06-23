package yaseerfarah22.com.ozet_design.Model;

import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2/21/2019.
 */

public class Array_Helper implements Serializable {

//The *transient* keyword in Java is used to indicate that a field should not be part of the serialization
   transient List array;
   transient View imageView;

    public Array_Helper(View imageView) {
        this.imageView = imageView;
    }

    public View getImageView() {
        return imageView;
    }

    public void setImageView(View imageView) {
        this.imageView = imageView;
    }

    public Array_Helper(List array) {
        this.array = array;
    }

    public List getArray() {
        return array;
    }

    public void setArray(List array) {
        this.array = array;
    }
}
