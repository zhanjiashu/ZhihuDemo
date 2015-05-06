package com.jiashu.zhihudemo.event;

/**
 * Created by Jiashu on 2015/5/5.
 */
public class FetchCompletedEvent {
    public String response;

    public FetchCompletedEvent(String response) {
        this.response = response;
    }
}
