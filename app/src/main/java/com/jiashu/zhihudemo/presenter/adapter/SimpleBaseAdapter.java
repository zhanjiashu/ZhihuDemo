package com.jiashu.zhihudemo.presenter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jiashu.zhihudemo.vu.Vu;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Jiashu on 2015/4/23.
 * 基于 MVP 模式, 作为 Presenter 的 通用 adapter
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

    /**
     * 针对 item 的 视图 操作
     * @param position
     */
    protected abstract void onBindListItemVu(int position);

    /**
     * 返回所 绑定 的 item 视图
     * @return
     */
    protected abstract Class<V> getVuClass();

    /**
     * 在 adapter 的数据后 追加数据
     * @param data
     */
    public void addAll(List<E> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 替换 adapter 中的数据
     * @param data
     */
    public void replace(List<E> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

}
