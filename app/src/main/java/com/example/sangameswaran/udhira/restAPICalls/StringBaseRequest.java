package com.example.sangameswaran.udhira.restAPICalls;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Sangameswaran on 20-11-2017.
 */

public class StringBaseRequest extends StringRequest {

    public StringBaseRequest(int method, String url, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(
                10000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public StringBaseRequest(int method, String url, Response.Listener<String> listener,
                             Response.ErrorListener errorListener, int timeOut, int retries) {
        super(method, url, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(
                timeOut, retries,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
