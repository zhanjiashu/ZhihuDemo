package com.jiashu.zhihudemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.jiashu.zhihudemo.vu.Vu;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Jiashu on 2015/4/23.
 */
public abstract class SimpleBaseAdapter<V extends Vu, E> extends BaseAdapter {

    V mVu;

    List<E> mData;

    public SimpleBaseAdapter(List<E> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public E getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            try {
                mVu = getVuClass().newInstance();
                mVu.initView(inflater, parent);
                convertView = mVu.getView();
                convertView.setTag(mVu);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            mVu = (V) convertView.getTag();
        }
        
        if (convertView != null) {
            onBindListItemVu(position);
        }
        
        return convertView;
    }

    protected abstract void onBindListItemVu(int position);


    protected abstract Class<V> getVuClass();
}
