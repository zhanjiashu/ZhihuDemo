package com.jiashu.zhihudemo.mode;

import android.text.TextUtils;

import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiashu on 2015/5/17.
 */
public class ZHFeed {
    private String mFeedId;
    private long mDataBlock;
    private int mDataOffset;

    private String mSourceName;
    private String mSourceSupplement;
    private String mSourceAvatarUrl;
    private String mSourceUrl;

    private String mTitle;
    private String mTitleUrl;

    private int mVoteups;
    private String mSummary;

    private String mAuthorUrl;
    private String mAuthorName;
    private String mAuthorHeadline;

    private boolean isVoteUp;
    private boolean isVoteDown;

    private String mContentUrl;

    private boolean isNohelped;
    private boolean isThanked;
    private int mComments;


    public String getFeedId() {
        return mFeedId;
    }

    public void setFeedId(String feedId) {
        mFeedId = feedId;
    }

    public long getDataBlock() {
        return mDataBlock;
    }

    public void setDataBlock(long dataBlock) {
        mDataBlock = dataBlock;
    }

    public int getDataOffset() {
        return mDataOffset;
    }

    public void setDataOffset(int dataOffset) {
        mDataOffset = dataOffset;
    }

    public String getSourceName() {
        return mSourceName;
    }

    public void setSourceName(String sourceName) {
        mSourceName = sourceName;
    }

    public String getSourceSupplement() {
        return mSourceSupplement;
    }

    public void setSourceSupplement(String sourceSupplement) {
        mSourceSupplement = sourceSupplement;
    }

    public String getSourceAvatarUrl() {
        return mSourceAvatarUrl;
    }

    public void setSourceAvatarUrl(String sourceAvatarUrl) {
        mSourceAvatarUrl = sourceAvatarUrl;
    }

    public String getSourceUrl() {
        return mSourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        mSourceUrl = sourceUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleUrl() {
        return mTitleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        mTitleUrl = titleUrl;
    }

    public int getVoteups() {
        return mVoteups;
    }

    public void setVoteups(int voteups) {
        mVoteups = voteups;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getAuthorUrl() {
        return mAuthorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        mAuthorUrl = authorUrl;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String authorName) {
        mAuthorName = authorName;
    }

    public String getAuthorHeadline() {
        return mAuthorHeadline;
    }

    public void setAuthorHeadline(String authorHeadline) {
        mAuthorHeadline = authorHeadline;
    }

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

    public String getContentUrl() {
        return mContentUrl;
    }

    public void setContentUrl(String contentUrl) {
        mContentUrl = contentUrl;
    }

    public boolean isNohelped() {
        return isNohelped;
    }

    public void setIsNohelped(boolean isNohelped) {
        this.isNohelped = isNohelped;
    }

    public boolean isThanked() {
        return isThanked;
    }

    public void setIsThanked(boolean isThanked) {
        this.isThanked = isThanked;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(int comments) {
        mComments = comments;
    }

    @Override
    public String toString() {
        return "ZHFeed{" +
                "mFeedId='" + mFeedId + '\'' +
                ", mSourceName='" + mSourceName + '\'' +
                ", mSourceSupplement='" + mSourceSupplement + '\'' +
                ", mSourceAvatarUrl='" + mSourceAvatarUrl + '\'' +
                ", mSourceUrl='" + mSourceUrl + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mTitleUrl='" + mTitleUrl + '\'' +
                ", mVoteups=" + mVoteups +
                ", mSummary='" + mSummary + '\'' +
                ", mAuthorUrl='" + mAuthorUrl + '\'' +
                ", mAuthorName='" + mAuthorName + '\'' +
                ", mAuthorHeadline='" + mAuthorHeadline + '\'' +
                ", isVoteUp=" + isVoteUp +
                ", isVoteDown=" + isVoteDown +
                ", mContentUrl='" + mContentUrl + '\'' +
                ", isNohelped=" + isNohelped +
                ", isThanked=" + isThanked +
                ", mComments=" + mComments +
                '}';
    }

    public static class Builder {

        private static final String TAG = "ZHFeedBuider";
        private ZHFeed mFeed;
        private Element mElt;

        public Builder (Element element) {
            mFeed = new ZHFeed();

            mElt = element;

        }

        public ZHFeed create() {
            String feedId = mElt.attr("id").replace("feed-", "");

            String dataBlockStr =mElt.attr("data-block");

            long dataBlock = 0;

            if (!TextUtils.isEmpty(dataBlockStr)) {
                dataBlock = Long.valueOf(dataBlockStr);
            }

            String dataOffsetStr = mElt.attr("data-offset");

            int dataOffset = 0;

            if (!TextUtils.isEmpty(dataOffsetStr)) {
                dataOffset = Integer.valueOf(dataOffsetStr);
            }


            String reactorStr = mElt.select("meta[itemprop=ZReactor]").attr("data-meta");


            int comments = 0;
            int voteups = 0;
            String feedType = null;
            try {
                JSONObject reactorJson = new JSONObject(reactorStr);
                feedType = reactorJson.getString("source_type");
                voteups = reactorJson.getInt("voteups");
                comments = reactorJson.getInt("comments");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Elements sourceElts = mElt.select("div[class=avatar]>a");
            String sourceAvatarUrl = mElt.select("img").attr("src");
            String sourceName = sourceElts.attr("title");
            String sourceUrl = sourceElts.attr("href");


            Elements contentElts = mElt.select("div[class=content]");

            Elements titleElts = contentElts.select("h2>a");
            String title = titleElts.text();
            String titleUrl = titleElts.attr("href");


            //String voteups = contentElts.select("div[class=zm-item-vote]>a").text();

            boolean isVoteUp = false;
            boolean isVoteDown = false;
            String votebar = contentElts.select("div[class=zm-votebar]").html();

            if (votebar.indexOf("down pressed") != -1) {
                isVoteDown = true;
            } else if (votebar.indexOf("up pressed") != -1) {
                isVoteUp = true;
            }

            Elements authorElts = contentElts.select("div[class=zm-item-answer-detail]>" +
                    "div[class=zm-item-answer-author-info]>h3");

            String authorName = authorElts.text().split("，")[0];
            String authorHeadline = authorElts.select("strong").text();


            String authorUrl = authorElts.select("a").attr("href");

            String sourceSupplement = null;

            switch (feedType) {
                case HttpConstants.ANSWER_MEMBER_VOTEUP:
                    sourceSupplement = "赞同该回答";
                    break;
                case HttpConstants.ANSWER_MEMBER:
                    sourceSupplement = "回答了该问题";
                    authorUrl = sourceUrl;
                    authorName = sourceName;
                    break;
                case HttpConstants.QUESTION_MEMBER_ASK:
                    sourceSupplement = "提了一个问题";
                    break;
                case HttpConstants.QUESTION_MEMBER_FOLLOW:
                    sourceSupplement = "关注了该问题";
                    break;
                case HttpConstants.COLUMN_MEMBER_FOLLOW:
                    sourceSupplement = "关注了该专栏";
                    break;
                case HttpConstants.ARTICLE_MEMBER_CREATE:
                case HttpConstants.ARTICLE_COLUMN_CREATE:
                    sourceSupplement = "发表了文章";
                    break;
                case HttpConstants.ARTICLE_MEMBER_VOTEUP:
                    sourceSupplement = "赞同该文章";
                    break;
                case HttpConstants.ROUNDTABLE_MEMBER_FOLLOW:
                    sourceSupplement = "关注了圆桌";
                    break;

                case HttpConstants.ANSWER_PROMOTION:
                    sourceSupplement = "热门回答";
                    sourceName = "";
                    break;
                default:
                    sourceSupplement = "来自";
            }


            Elements summaryElts = contentElts.select("div[class=zh-summary summary clearfix]");
            String summary = summaryElts.text();

            if ("显示全部".equals(summary) && summaryElts.select("img") != null) {
                summary = "[图片]";
            }

            summary = summary.replace("显示全部", "");

            String contentUrl = contentElts.select("div[class=zh-summary summary clearfix]>a[class=toggle-expand]").attr("href");

/*            if (TextUtils.isEmpty(contentUrl)) {
                // Jsoup 无法解析这段 html, 通过 正则表达式去匹配所需要的 内容详情url
                contentUrl = contentElts.select("textarea[class=content hidden]").text();
                Pattern pattern = Pattern.compile("href=\".*\"");
                Matcher matcher = pattern.matcher(contentUrl);
                if (matcher.find()) {
                    contentUrl = matcher.group(0);
                }

                contentUrl = contentUrl.replace("href=\"","").replace("\"","").trim();
            }*/
            if (TextUtils.isEmpty(contentUrl)) {
                // Jsoup 无法解析这段 html, 通过 正则表达式去匹配所需要的 内容详情url
                contentUrl = contentElts.select("textarea[class=content hidden]").text();
                Pattern tagPattern = Pattern.compile("<span class=\"answer-date-link-wrap\">.*</span>");
                Matcher tagMatcher = tagPattern.matcher(contentUrl);

                if (tagMatcher.find()) {
                    contentUrl = tagMatcher.group(0);
                }

                Pattern pattern = Pattern.compile("href=\".*\"");
                Matcher matcher = pattern.matcher(contentUrl);
                if (matcher.find()) {
                    contentUrl = matcher.group(0);
                }

                contentUrl = contentUrl.replace("href=\"","").replace("\"","").trim();
            }

            Elements panelElts = contentElts.select("div[class=feed-meta]");

            String thanks = panelElts.select("a[name=thanks]").attr("data-thanked");
            String nohelp = mElt.select("a[name=nohelp]").text();

            boolean isThanked = false;
            boolean isNoHelped = false;

            if (thanks != null && "true".equals(thanks)) {
                isThanked = true;
            }

            if (nohelp != null && "true".equals(nohelp)) {
                isNoHelped = true;
            }

            LogUtils.d(TAG, "thanks = " + thanks);
            LogUtils.d(TAG, "nohelp = " + nohelp);
            LogUtils.d(TAG, "===============");

            mFeed.setFeedId(feedId);
            mFeed.setDataBlock(dataBlock);
            mFeed.setDataOffset(dataOffset);

            mFeed.setSourceName(sourceName);
            mFeed.setSourceSupplement(sourceSupplement);
            mFeed.setSourceAvatarUrl(sourceAvatarUrl);
            mFeed.setSourceUrl(fixURL(sourceUrl));

            mFeed.setTitle(title);
            mFeed.setTitleUrl(fixURL(titleUrl));

            mFeed.setVoteups(voteups);
            mFeed.setComments(comments);
            mFeed.setSummary(summary);

            mFeed.setAuthorName(authorName);
            mFeed.setAuthorHeadline(authorHeadline);
            mFeed.setAuthorUrl(fixURL(authorUrl));

            mFeed.setContentUrl(fixURL(contentUrl));

            mFeed.setIsVoteUp(isVoteUp);
            mFeed.setIsVoteDown(isVoteDown);

            mFeed.setIsThanked(isThanked);
            mFeed.setIsNohelped(isNoHelped);

            return mFeed;
        }

        /**
         * 检查解析得到的 url 是否完整，不完整则添加前缀 HOST
         * @param url
         * @return
         */
        private String fixURL(String url) {
            if (TextUtils.isEmpty(url) || url.startsWith("http://")) {
                return url;
            }
            return HttpConstants.HOST + url;
        }
    }
}
