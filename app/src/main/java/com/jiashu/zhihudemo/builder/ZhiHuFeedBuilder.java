package com.jiashu.zhihudemo.builder;

import android.nfc.Tag;

import com.jiashu.zhihudemo.data.NetConstants;
import com.jiashu.zhihudemo.mode.ZhiHuFeed;
import com.jiashu.zhihudemo.utils.LogUtil;
import com.jiashu.zhihudemo.utils.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Jiashu on 2015/5/4.
 */
public class ZhiHuFeedBuilder {

    private static final String TAG = "ZhiHuFeedBuilder";
    private Element element;
    private ZhiHuFeed feed;

    public ZhiHuFeedBuilder(Element element) {
        this.element = element;
        feed = new ZhiHuFeed();
    }

    public ZhiHuFeedBuilder setAvatar() {
        Elements avatarElts = element.select("div[class=feed-item-inner]>div[class=avatar]>a");

        String avatarImgUrl = avatarElts.select("img").attr("src");
        LogUtil.d(TAG, "avatar = " + avatarImgUrl);
        feed.setAvatarImgUrl(avatarImgUrl);
        return this;
    }

    public ZhiHuFeedBuilder setSource() {
        Elements sourceElts = element.select("div[class=source]");
        // 获取 答题者、专栏作者 或者 话题名称
        String authorName = sourceElts.select("a:not(.column_link)").text();
        authorName = authorName.replace("关注话题","").trim();
        // 获取 答题者的个人主页连接 或者 专栏、话题 的主页链接
        String authorUrl = sourceElts.select("a:not(.column_link)").attr("href");
        authorUrl = fixURL(authorUrl);

        // 获取 source ，包含 Feed类型、点赞数、评论数等
        String sourceStr = element.select("meta[itemprop=ZReactor]").attr("data-meta");

        String feedType = null;
        int voteups = 0;
        int comments = 0;
        try {
            JSONObject jsonSource = new JSONObject(sourceStr);
            feedType = jsonSource.getString("source_type");
            // 此类型，source无 voteups
            if (!feedType.equals("member_follow_question") || !feedType.equals("topic_popular_question")) {
                voteups = jsonSource.getInt("voteups");
            }

            comments = jsonSource.getInt("comments");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        feed.setAuthor(authorName);
        feed.setAuthorUrl(authorUrl);
        feed.setFeedType(feedType);
        feed.setVoteups(voteups);
        feed.setComments(comments);

        LogUtil.d(TAG, "authorName = " + authorName);
        LogUtil.d(TAG, "autorUrl =" + authorUrl);
        LogUtil.d(TAG, "feedType = " + feedType);
        LogUtil.d(TAG, "voteups = " + voteups);
        LogUtil.d(TAG, "comments = " + comments);
        return this;
    }

    public ZhiHuFeedBuilder setContent() {
        Elements contentElts = element.select("div[class=content]");
        // 问题 或者 文章标题
        String title = contentElts.select("h2>a").text();
        // 问题链接 或者 文章链接
        String titleUrl = contentElts.select("h2>a").attr("href");
        titleUrl = fixURL(titleUrl);

        // 答案 或者 文章 的摘要
        String summary = contentElts.select("div[class=zh-summary summary clearfix]").text();

        String contentUrl = contentElts.select("div[class=zh-summary summary clearfix]>a").attr("href");
        contentUrl = fixURL(contentUrl);

        feed.setTitle(title);
        feed.setTitleUrl(titleUrl);
        feed.setSummary(summary);
        feed.setContentUrl(contentUrl);

        LogUtil.d(TAG, "title = " + title);
        LogUtil.d(TAG, "titleUrl = " + titleUrl);
        LogUtil.d(TAG, "summary = " + summary);
        LogUtil.d(TAG, "contentUrl = " + contentUrl);
        return this;
    }

    public ZhiHuFeed builde() {
        return feed;
    }

    /**
     * 检查解析得到的 url 是否完整，不完整则添加前缀 HOST
     * @param url
     * @return
     */
    private String fixURL(String url) {
        if (url.startsWith(NetConstants.HOST)) {
            return url;
        }
        return NetConstants.HOST + url;
    }
}
