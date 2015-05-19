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
 * Created by Jiashu on 2015/5/17.
 */
public class ZHFeed {
    private String mFeedId;
    private long mDataBlock;
    private int mDataOffset;

    private String feedType;

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

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
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

        /**
         * 设置 feedId 、 blockId、 offset
         * @return
         */
        public Builder setFeedId() {
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

            mFeed.setFeedId(feedId);
            mFeed.setDataBlock(dataBlock);
            mFeed.setDataOffset(dataOffset);

            return this;
        }

        /**
         * 设置 feedType 、 voteups(赞同数) 以及 comments(评论数)
         * @return
         */
        public Builder setVoteups() {
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

            mFeed.setFeedType(feedType);
            mFeed.setVoteups(voteups);
            mFeed.setComments(comments);

            return this;
        }

        /**
         * 设置 来源的头像、名字、主页以及 来源的附加说明
         * @return
         */
        public Builder setSource() {

            Elements sourceElts = mElt.select("div[class=avatar]>a");
            String sourceAvatarUrl = mElt.select("img").attr("src");
            String sourceName = sourceElts.attr("title");
            String sourceUrl = sourceElts.attr("href");

            String sourceSupplement = null;

            switch (mFeed.getFeedType()) {
                case HttpConstants.ANSWER_MEMBER_VOTEUP:
                    sourceSupplement = "赞同该回答";
                    break;
                case HttpConstants.ANSWER_MEMBER:
                    sourceSupplement = "回答了该问题";
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

            mFeed.setSourceName(sourceName);
            mFeed.setSourceSupplement(sourceSupplement);
            mFeed.setSourceAvatarUrl(sourceAvatarUrl);
            mFeed.setSourceUrl(fixURL(sourceUrl));

            return this;
        }

        /**
         * 设置 问题或文章标题、问题或文章的url、回答或文章的摘要以及查看全部内容的url
         *     答题者或文章作者的 名字、签名、url
         *     设置当前用户对 这条 feed信息 的 投票状态：赞同、反对、没有帮助、感谢
         * @return
         */
        public Builder setContent() {

            Elements contentElts = mElt.select("div[class=content]");

            Elements titleElts = contentElts.select("h2>a");
            String title = titleElts.text();
            String titleUrl = titleElts.attr("href");


            boolean isVoteUp = false;
            boolean isVoteDown = false;

            Elements votebarElts = contentElts.select("div[class=zm-votebar]>button");

            if (votebarElts.size() >= 2) {
                if ("up pressed".equals(votebarElts.get(0).attr("class"))) {
                    isVoteUp = true;
                } else if ("down pressed".equals(votebarElts.get(1).attr("class"))) {
                    isVoteDown = true;
                }
            }

            Elements authorElts = contentElts.select("div[class=zm-item-answer-detail]>" +
                    "div[class=zm-item-answer-author-info]>h3");

            String authorName = authorElts.text().split("，")[0];
            String authorHeadline = authorElts.select("strong").text();


            String authorUrl = authorElts.select("a").attr("href");

            if (mFeed.getFeedType().equals(HttpConstants.ANSWER_MEMBER)) {
                authorUrl = mFeed.getSourceUrl();
                authorName = mFeed.getSourceName();
            }

            Elements summaryElts = contentElts.select("div[class=zh-summary summary clearfix]");
            String summary = summaryElts.text();

            if ("显示全部".equals(summary) && summaryElts.select("img") != null) {
                summary = "[图片]";
            }

            summary = summary.replace("显示全部", "");

            String contentUrl = contentElts.select("div[class=zh-summary summary clearfix]>a[class=toggle-expand]").attr("href");

            if (TextUtils.isEmpty(contentUrl)) {
                String contentHidden = contentElts.select("textarea[class=content hidden]").text();
                // Jsoup 无法遍历 textarea 标签下的子节点，需要获取其内容后再通过 Jsoup 解析
                Document contentDoc = Jsoup.parse(contentHidden);
                contentUrl = contentDoc.select("span[class=answer-date-link-wrap]>a").attr("href");
            }

            Elements panelElts = contentElts.select("div[class=feed-meta]");

            String thanks = panelElts.select("a[name=thanks]").attr("data-thanked");
            String nohelp = panelElts.select("a[name=nohelp]").attr("data-revert");


            boolean isThanked = false;
            boolean isNoHelped = false;

            if (thanks != null && "true".equals(thanks)) {
                isThanked = true;
            }

            if (nohelp != null && "true".equals(nohelp)) {
                isNoHelped = true;
            }

            mFeed.setTitle(title);
            mFeed.setTitleUrl(fixURL(titleUrl));
            mFeed.setSummary(summary);

            mFeed.setAuthorName(authorName);
            mFeed.setAuthorHeadline(authorHeadline);
            mFeed.setAuthorUrl(fixURL(authorUrl));

            mFeed.setContentUrl(fixURL(contentUrl));

            mFeed.setIsVoteUp(isVoteUp);
            mFeed.setIsVoteDown(isVoteDown);

            mFeed.setIsThanked(isThanked);
            mFeed.setIsNohelped(isNoHelped);

            return this;
        }

        public ZHFeed create() {

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
