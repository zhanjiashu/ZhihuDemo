package com.jiashu.zhihudemo.data;

import com.jiashu.zhihudemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiashu on 2015/4/23.
 */
public class Constants {
    public static final String DEFAULT_TITLE = "知乎";
    public static final String[] NAV_NAMES = new String[] {
            "首页", "发现", "关注",
            "收藏", "草稿", "提问",
    };
    public static final int[] NAV_ICONS = {
            R.drawable.ic_action_toc,
            R.drawable.ic_action_explore,
            R.drawable.ic_action_visibility,
            R.drawable.ic_action_bookmark,
            R.drawable.ic_action_description,
            R.drawable.ic_action_add,
    };

    public static List<Map<String, Object>> NAV_LIST_DATA;
    public static final String KEY_NAV_NAME = "navName";
    public static final String KEY_NAV_ICON = "navIcon";

    static {
        NAV_LIST_DATA = new ArrayList<>();

        for (int i = 0; i < NAV_NAMES.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put(KEY_NAV_NAME, NAV_NAMES[i]);
            map.put(KEY_NAV_ICON, NAV_ICONS[i]);
            NAV_LIST_DATA.add(map);
        }
    }

    public static final String QUIZ_TITLE = "添加问题";
    public static final String[] QUIZ_TAB_TITLES = {
            "问题", "描述", "话题",
    };

    public static final String[] DISCOVERY_TAB_TITLES = {
            "热门推荐", "热门收藏", "本月热榜", "今日热榜"
    };

    public static final String[] FOLLOW_TAB_TITLES = {
            "关注的问题", "关注的收藏", "关注的话题", "关注的专栏"
    };
}
