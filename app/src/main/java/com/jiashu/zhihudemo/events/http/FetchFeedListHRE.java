package com.jiashu.zhihudemo.events.http;

import com.jiashu.zhihudemo.mode.ZHFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/27.
 */
public class FetchFeedListHRE extends HttpResponseEvent<List<ZHFeed>> {
    public FetchFeedListHRE(List<ZHFeed> feedList) {
        super.data = feedList != null ? feedList : new ArrayList<ZHFeed>();
    }
}
