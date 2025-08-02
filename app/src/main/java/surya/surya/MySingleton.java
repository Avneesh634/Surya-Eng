package surya.surya;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static Context mCtx;
    private static MySingleton mInstance;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    private MySingleton(Context context) {
        mCtx = context;
        RequestQueue requestQueue = getRequestQueue();
        mRequestQueue = requestQueue;
        mImageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized MySingleton getInstance(Context context) {
        MySingleton mySingleton;
        synchronized (MySingleton.class) {
            if (mInstance == null) {
                mInstance = new MySingleton(context);
            }
            mySingleton = mInstance;
        }
        return mySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
