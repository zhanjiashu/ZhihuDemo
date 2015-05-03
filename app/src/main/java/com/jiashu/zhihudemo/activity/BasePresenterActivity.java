package com.jiashu.zhihudemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jiashu.zhihudemo.vu.Vu;

import de.greenrobot.event.EventBus;

/**
 * Created by Jiashu on 2015/4/23.
 */
public abstract class BasePresenterActivity<V extends Vu> extends ActionBarActivity {

    V mVu;
    FragmentManager mFm;
    EventBus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFm = getSupportFragmentManager();
        mBus = EventBus.getDefault();
        try {
            mVu = getVuClass().newInstance();
            mVu.initView(getLayoutInflater(), null);
            setContentView(mVu.getView());
            onBindVu();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        afterResume();
    }

    @Override
    protected void onPause() {
        beforePause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        onDestroyVu();
        mVu = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mVu.initMenu(getMenuInflater(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        onBindMenu(id);
        return super.onOptionsItemSelected(item);
    }

    protected void onBindMenu(int menuItem) {
    }

    protected abstract Class<V> getVuClass();

    protected abstract void onBindVu();

    protected abstract void onDestroyVu();

    protected void afterResume() {}

    protected void beforePause() {}

}
