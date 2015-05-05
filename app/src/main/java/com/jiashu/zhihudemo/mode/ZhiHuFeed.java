package com.jiashu.zhihudemo.mode;

import com.jiashu.zhihudemo.data.NetConstants;
import com.jiashu.zhihudemo.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 封装 知乎内容 的bean
 * Created by Jiashu on 2015/5/4.
 */
public class ZhiHuFeed {

    public static final int SUPP_LEFT = 10;
    public static final int SUPP_RIGHT = 11;

    private String source;  // 专栏作者 或 答题者 或 来源话题

    private String sourceSupp; // 附加给 source 的信息

    private int suppSide;       // 附加信息相对于 source 的位置

    private String sourceUrl;   // 专栏主页、个人主页、话题主页


    private String avatarImgUrl;    // 头像url

    private String title;   // 专栏文章标题 或 问题

    private String titleUrl;    // 专栏文章链接 或 问题链接

    private String summary; // 文章内容摘要 或 答案摘要

    private String contentUrl;  // 查看全部 内容 的链接

    private String feedType;  // content的类型

    private int voteups;    // 点赞数

    private int comments;   // 评论数

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

    public String getAvatarImgUrl() {
        return avatarImgUrl;
    }

    public void setAvatarImgUrl(String avatarImgUrl) {
        this.avatarImgUrl = avatarImgUrl;
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
                "suppSide=" + suppSide +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", avatarImgUrl='" + avatarImgUrl + '\'' +
                ", title='" + title + '\'' +
                ", titleUrl='" + titleUrl + '\'' +
                ", summary='" + summary + '\'' +
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

            String suppMsg;
            int suppSide;

            switch (feedType) {
                case NetConstants.ANSWER_MEMBER_VOTEUP:
                    suppMsg = "赞同该回答";
                    suppSide = SUPP_RIGHT;
                    break;
                case NetConstants.ANSWER_MEMBER:
                    suppMsg = "回答了该问题";
                    suppSide = SUPP_RIGHT;
                    break;
                case NetConstants.QUESTION_MEMBER_FOLLOW:
                    suppMsg = "关注了该问题";
                    suppSide = SUPP_RIGHT;
                    break;
                case NetConstants.COLUMN_MEMBER_FOLLOW:
                    suppMsg = "关注了该专栏";
                    suppSide = SUPP_RIGHT;
                    break;
                case NetConstants.ARTICLE_MEMBER_CREATE:
                    suppMsg = "发表了文章";
                    suppSide = SUPP_RIGHT;
                    break;
                case NetConstants.ARTICLE_MEMBER_VOTEUP:
                    suppMsg = "赞同了文章";
                    break;
                case NetConstants.ANSWER_PROMOTION:
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

            String summary = contentElts.select("div[class=zh-summary summary clearfix]").text();

            mFeed.setFeedType(feedType);
            mFeed.setVoteups(voteups);
            mFeed.setComments(comments);
            mFeed.setAvatarImgUrl(avatarImgUrl);
            mFeed.setSource(source);
            mFeed.setSourceUrl(fixURL(sourceUrl));
            mFeed.setSourceSupp(suppMsg);
            mFeed.setSuppSide(suppSide);
            mFeed.setTitle(title);
            mFeed.setTitleUrl(fixURL(titleUrl));
            mFeed.setSummary(summary);
            LogUtil.d(TAG, mFeed.toString());
            return mFeed;
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
}
