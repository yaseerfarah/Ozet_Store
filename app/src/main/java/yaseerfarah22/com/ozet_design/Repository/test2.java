package yaseerfarah22.com.ozet_design.Repository;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by DELL on 3/4/2019.
 */

public class test2 {


    private static test2 instance;
    private RequestQueue requestQueue;
    private static Context ctx;


    private test2(Context context) {


        ctx=context;

        requestQueue=getRequestQueue();
    }


    public void addToRequestQueue(JsonObjectRequest req) {
        getRequestQueue().add(req);
    }


    public static synchronized test2 getInstance(Context context) {
        if (instance == null) {
            instance = new test2(context);
        }
        return instance;
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
}
