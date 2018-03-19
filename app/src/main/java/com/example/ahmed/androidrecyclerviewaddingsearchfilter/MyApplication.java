package com.example.ahmed.androidrecyclerviewaddingsearchfilter;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ahmed on 3/18/2018.
 */

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();
    RequestQueue queue;
    static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static synchronized MyApplication getInstance() {
        return application;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }
        return queue;
    }

    public void addToRequestQueue(Request request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public void addToRequestQueue(Request request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }
}
