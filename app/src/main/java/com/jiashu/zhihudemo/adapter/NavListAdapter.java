package com.jiashu.zhihudemo.adapter;

import com.jiashu.zhihudemo.data.Constants;
import com.jiashu.zhihudemo.vu.NavListItemVu;

import java.util.List;
import java.util.Map;

/**
 * Created by Jiashu on 2015/4/24.
 */
public class NavListAdapter extends SimpleBaseAdapter<NavListItemVu, Map<String, Object>> {


    public NavListAdapter(List<Map<String, Object>> data) {
        super(data);
    }

    @Override
    protected void onBindListItemVu(int position) {
        Map<String, Object> map = mData.get(position);
        mVu.setNavName((CharSequence) map.get(Constants.KEY_NAV_NAME));
        mVu.setNavIcon((Integer) map.get(Constants.KEY_NAV_ICON));
    }

    @Override
    protected Class<NavListItemVu> getVuClass() {
        return NavListItemVu.class;
    }
}
