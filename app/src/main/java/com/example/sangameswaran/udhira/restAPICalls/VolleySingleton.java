package com.example.sangameswaran.udhira.restAPICalls;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }


  /*  package com.nc.skadi.restClient;

    import com.android.volley.DefaultRetryPolicy;
    import com.android.volley.NetworkResponse;
    import com.android.volley.Response;
    import com.android.volley.toolbox.JsonArrayRequest;

    import org.json.JSONArray;

    class JsonArrayBaseRequest extends JsonArrayRequest {

        public JsonArrayBaseRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
            setRetryPolicy(new DefaultRetryPolicy(
                    10000, 3,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

        public JsonArrayBaseRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener, int timeOut, int retries) {
            super(url, listener, errorListener);
            setRetryPolicy(new DefaultRetryPolicy(
                    timeOut, retries,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            return super.parseNetworkResponse(response);
        }

    }*/


}
