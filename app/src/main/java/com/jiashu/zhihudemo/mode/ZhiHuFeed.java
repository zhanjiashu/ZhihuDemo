package com.jiashu.zhihudemo.mode;

import android.text.TextUtils;

import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiashu on 2015/5/4.
 *
 * 封装 【知乎】首页 的一条动态内容：Feed
 */
public class ZhiHuFeed {

    public static final int SUPP_LEFT = 10;
    public static final int SUPP_RIGHT = 11;

    private String feedID;

    private String source;  // 你所关注的 人、专栏、话题

    private String sourceSupp; // 附加给 source 的信息

    private int suppSide;       // 附加信息相对于 source 的位置

    private String sourceUrl;   // 专栏主页、个人主页、话题主页

    private String authorName;  // 答题人

    private String authorProfile;   // 答题人的简介

    private String authorUrl;

    private String avatarUrl;    // 头像url

    private String title;   // 专栏文章标题 或 问题

    private String titleUrl;    // 专栏文章链接 或 问题链接

    private String summary; // 文章内容摘要 或 答案摘要

    private String content;     // 全部内容

    private String contentUrl;  // 查看全部 内容 的链接

    private String feedType;  // content的类型

    private int voteups;    // 点赞数

    private int comments;   // 评论数

    public String getFeedID() {
        return feedID;
    }

    public void setFeedID(String feedID) {
        this.feedID = feedID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceSupp() {
        return sourceSupp;
    }

    public void setSourceSupp(String sourceSupp) {
        this.sourceSupp = sourceSupp;
    }

    public int getSuppSide() {
        return suppSide;
    }

    public void setSuppSide(int suppSide) {
        this.suppSide = suppSide;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(String authorProfile) {
        this.authorProfile = authorProfile;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public int getVoteups() {
        return voteups;
    }

    public void setVoteups(int voteups) {
        this.voteups = voteups;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        return "ZhiHuFeed{" +
                "feedID='" + feedID + '\'' +
                ", source='" + source + '\'' +
                ", sourceSupp='" + sourceSupp + '\'' +
                ", suppSide=" + suppSide +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorProfile='" + authorProfile + '\'' +
                ", authorUrl='" + authorUrl + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", title='" + title + '\'' +
                ", titleUrl='" + titleUrl + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", feedType='" + feedType + '\'' +
                ", voteups=" + voteups +
                ", comments=" + comments +
                '}';
    }

    public static class Builder {

        private static final String TAG = "ZhiHuFeed.Builder";
        private ZhiHuFeed mFeed;

        private Element mElt;

        public Builder(Element element) {
            mElt = element;
            mFeed = new ZhiHuFeed();
        }

        public ZhiHuFeed build(){

            String feedID = mElt.select("div[id^=feed-]").attr("id");
            feedID = feedID.replace("feed-","");

            String reactorStr = mElt.select("meta[itemprop=ZReactor]").attr("data-meta");

            String feedType = null;
            int voteups = 0;
            int comments = 0;
            try {
                JSONObject reactorJson = new JSONObject(reactorStr);
                feedType = reactorJson.getString("source_type");
                voteups = reactorJson.getInt("voteups");
                comments = reactorJson.getInt("comments");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String avatarImgUrl = mElt.select("div[class=avatar]>a>img").attr("src");
            String source = mElt.select("div[class=avatar]>a").attr("title");
            String sourceUrl = mElt.select("div[class=avatar]>a").attr("href");

            Elements authorElts = mElt.select("div[class=zm-item-answer-detail]>" +
                    "div[class=zm-item-answer-author-info]>h3");

            String authorName = authorElts.select("a").text();
            String authorUrl = authorElts.select("a").attr("href");
            String authorProfile = authorElts.select("strong").text();

            if (TextUtils.isEmpty(authorName)) {
                authorName = authorElts.text();
                if (TextUtils.isEmpty(authorName)) {
                    authorName = source;
                }
            }

            String suppMsg;
            int suppSide;

            switch (feedType) {
                case HttpConstants.ANSWER_MEMBER_VOTEUP:
                    suppMsg = "赞同该回答";
                    break;
                case HttpConstants.ANSWER_MEMBER:
                    suppMsg = "回答了该问题";
                    authorUrl = sourceUrl;
                    break;
                case HttpConstants.QUESTION_MEMBER_ASK:
                    suppMsg = "提了一个问题";
                    break;
                case HttpConstants.QUESTION_MEMBER_FOLLOW:
                    suppMsg = "关注了该问题";
                    suppSide = SUPP_RIGHT;
                    break;
                case HttpConstants.COLUMN_MEMBER_FOLLOW:
                    suppMsg = "关注了该专栏";
                    break;
                case HttpConstants.ARTICLE_MEMBER_CREATE:
                case HttpConstants.ARTICLE_COLUMN_CREATE:
                    suppMsg = "发表了文章";
                    break;
                case HttpConstants.ARTICLE_MEMBER_VOTEUP:
                    suppMsg = "赞同了文章";
                    break;
                case HttpConstants.ROUNDTABLE_MEMBER_FOLLOW:
                    suppMsg = "关注了圆桌";
                    break;

                case HttpConstants.ANSWER_PROMOTION:
                    suppMsg = "热门回答";
                    source = "";
                    break;
                default:
                    suppMsg = "来自";
            }

            if ("来自".equals(suppMsg) || "热门回答".equals(suppMsg)) {
                suppSide = SUPP_LEFT;
            } else {
                suppSide = SUPP_RIGHT;
            }

            Elements contentElts = mElt.select("div[class=content]");

            String title = contentElts.select("h2>a").text();
            String titleUrl = contentElts.select("h2>a").attr("href");


/*            String content = contentElts.select("textarea[class=content hidden]").text();

            Document contentDoc = Jsoup.parse(content);

            String time = contentDoc.select("span[class=answer-date-link-wrap]>a").text();
            LogUtils.d(TAG, time);
            String timeHtml = "<br><br><span class=\"answer-date-link-wrap\" style=\"float:right\">\n" +
                    "<a class=\"answer-date-link meta-item\" target=\"_blank\" href=\"/question/30267463/answer/47398187\">"+ time +"</a>\n" +
                    "</span>";
            Pattern cntPattern = Pattern.compile("<span class=\"answer-date-link-wrap.*</span>");
            Matcher cntMatcher = cntPattern.matcher(content);
            if (cntMatcher.find()) {
                String lastSpan = cntMatcher.group(cntMatcher.groupCount());
                content = content.replace(lastSpan, timeHtml).trim();
            }*/

            //LogUtils.d(TAG, content);
            Elements summaryElts = contentElts.select("div[class=zh-summary summary clearfix]");
            String summary = summaryElts.text();

            // 当 内容摘要 summary 为纯图片的时，指定summary的显示内容为：[图片]
            if (summary.equals("显示全部") && (summaryElts.select("img") != null)) {
                summary = "[图片]";
            }

            String contentUrl = contentElts.select("div[class=zh-summary summary clearfix]>a[class=toggle-expand]").attr("href");

            if (TextUtils.isEmpty(contentUrl)) {
                // Jsoup 无法解析这段 html, 通过 正则表达式去匹配所需要的 内容详情url
                contentUrl = contentElts.select("textarea[class=content hidden]").text();
                Pattern pattern = Pattern.compile("href=\".*\"");
                Matcher matcher = pattern.matcher(contentUrl);
                if (matcher.find()) {
                    contentUrl = matcher.group(0);
                }

                contentUrl = contentUrl.replace("href=\"","").replace("\"","").trim();
            }

            LogUtils.d(TAG, "contentUrl = " + contentUrl);
            mFeed.setFeedID(feedID);
            mFeed.setFeedType(feedType);
            mFeed.setVoteups(voteups);
            mFeed.setComments(comments);
            mFeed.setAuthorName(authorName);
            mFeed.setAuthorUrl(fixURL(authorUrl));
            mFeed.setAuthorProfile(authorProfile);
            mFeed.setAvatarUrl(avatarImgUrl);
            mFeed.setSource(source);
            mFeed.setSourceUrl(fixURL(sourceUrl));
            mFeed.setSourceSupp(suppMsg);
            mFeed.setSuppSide(suppSide);
            mFeed.setTitle(title);
            mFeed.setTitleUrl(fixURL(titleUrl));

            mFeed.setSummary(summary);
            mFeed.setContentUrl(fixURL(contentUrl));
            //LogUtils.d(TAG, mFeed.toString());
            return mFeed;
        }

        /**
         * 检查解析得到的 url 是否完整，不完整则添加前缀 HOST
         * @param url
         * @return
         */
        private String fixURL(String url) {
            if (url.startsWith("http://")) {
                return url;
            }
            return HttpConstants.HOST + url;
        }
    }
}
