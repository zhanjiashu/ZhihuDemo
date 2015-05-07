package com.jiashu.zhihudemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.jiashu.zhihudemo.data.StringConstants;
import com.jiashu.zhihudemo.event.FetchFailEvent;
import com.jiashu.zhihudemo.net.ZhiHuCookieStore;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class VolleyUtil {

    private static final String DAFAULT_CACHE_DIR = "ZhuHuCache";
    private static final int DEFAULT_THREAD_POOL_SIZE = 1;
    private static VolleyUtil mInstance;

    private Context mContext;
    private ConnectivityManager mCM;

    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    private CookieStore mCookieStore;

    private VolleyUtil(Context context) {
        mContext = context;

        mCM = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        mCookieStore = new ZhiHuCookieStore();

        mQueue = getQueue();

        mImageLoader = new ImageLoader(mQueue, new LruImageCache(context));
    }

    public static VolleyUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VolleyUtil.class) {
                if (mInstance == null) {
                    mInstance = new VolleyUtil(context);
                }
            }
        }
        return mInstance;
    }

    public <T> void addRequest(Request<T> request) {
        if (mCM.getActiveNetworkInfo() != null) {
            mQueue.add(request);
        } else {
            ToastUtils.show(StringConstants.TOAST_CHECK_NETWORK);
            // ActionBarPullToRefresh 的BUG,必须子线程存在
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new FetchFailEvent());
                }
            }).start();
        }
    }


    public RequestQueue getQueue() {
        if (mQueue == null) {
            DefaultHttpClient client = new DefaultHttpClient();
            client.setCookieStore(mCookieStore);
            Network network = new BasicNetwork(new HttpClientStack(client));

            File cacheDir = new File(mContext.getCacheDir(), DAFAULT_CACHE_DIR);

            mQueue = new RequestQueue(new DiskBasedCache(cacheDir), network, DEFAULT_THREAD_POOL_SIZE);
            mQueue.start();
        }
        return mQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void cancelAll() {
        mQueue.cancelAll(mContext);
    }

}
