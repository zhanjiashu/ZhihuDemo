package com.jiashu.zhihudemo.events;

/**
 * Created by Jiashu on 2015/5/15.
 */
public class VoteEvent {
    private boolean isVoteUp;
    private boolean isVoteDown;

    public boolean isVoteUp() {
        return isVoteUp;
    }

    public void setIsVoteUp(boolean isVoteUp) {
        this.isVoteUp = isVoteUp;
    }

    public boolean isVoteDown() {
        return isVoteDown;
    }

    public void setIsVoteDown(boolean isVoteDown) {
        this.isVoteDown = isVoteDown;
    }
}
