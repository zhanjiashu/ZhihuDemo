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

/**
 * Created by Jiashu on 2015/5/17.
 */
public class ZHFeed {
    private String mFeedId;
    private long mDataBlock;
    private int mDataOffset;
    private String mFeedType;

    private ZHMember mSource;
    private String mSourceType;
    private String mSourceSupplement;
    private String mSourceAvatarUrl;

    private String mTitle;
    private String mTitleUrl;

    private int mVoteupCount;
    private String mSummary;
    private String mContentUrl;
    private ZHAnswer mZHAnswer;

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
        return mFeedType;
    }

    public void setFeedType(String feedType) {
        mFeedType = feedType;
    }

    public ZHMember getSource() {
        return mSource;
    }

    public void setSource(ZHMember source) {
        mSource = source;
    }

    public String getSourceType() {
        return mSourceType;
    }

    public void setSourceType(String sourceType) {
        mSourceType = sourceType;
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

    public int getVoteupCount() {
        return mVoteupCount;
    }

    public void setVoteupCount(int voteupCount) {
        mVoteupCount = voteupCount;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getContentUrl() {
        return mContentUrl;
    }

    public void setContentUrl(String contentUrl) {
        mContentUrl = contentUrl;
    }

    public ZHAnswer getZHAnswer() {
        return mZHAnswer;
    }

    public void setZHAnswer(ZHAnswer ZHAnswer) {
        mZHAnswer = ZHAnswer;
    }

    @Override
    public String toString() {
        return "ZHFeed{" +
                "mFeedId='" + mFeedId + '\'' +
                ", mDataBlock=" + mDataBlock +
                ", mDataOffset=" + mDataOffset +
                ", mFeedType='" + mFeedType + '\'' +
                ", mSource=" + mSource +
                ", mSourceType='" + mSourceType + '\'' +
                ", mSourceSupplement='" + mSourceSupplement + '\'' +
                ", mSourceAvatarUrl='" + mSourceAvatarUrl + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mTitleUrl='" + mTitleUrl + '\'' +
                ", mSummary='" + mSummary + '\'' +
                ", mContentUrl='" + mContentUrl + '\'' +
                ", mZHAnswer=" + mZHAnswer +
                '}';
    }

    public static class Builder {

        private static final String TAG = "ZHFeedBuider";
        private ZHFeed mFeed;
        private Element mElt;

        private int mVoteups;
        private int mComments;

        public Builder(Element element) {
            mFeed = new ZHFeed();

            mElt = element;

        }

        public Builder setFeedParams() {

            String feedId = mElt.attr("id").replace("feed-", "");

            String feedType = mElt.attr("data-type");

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
            mFeed.setFeedType(feedType);
            mFeed.setDataBlock(dataBlock);
            mFeed.setDataOffset(dataOffset);

            return this;
        }

        public Builder setSource() {
            String reactorStr = mElt.select("meta[itemprop=ZReactor]").attr("data-meta");

            String sourceType = null;
            try {
                JSONObject reactorJson = new JSONObject(reactorStr);
                sourceType = reactorJson.getString("source_type");
                mVoteups = reactorJson.getInt("voteups");
                mComments = reactorJson.getInt("comments");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Elements sourceElts = mElt.select("div[class=avatar]>a");
            String sourceAvatarUrl = mElt.select("img").attr("src");
            String sourceName = sourceElts.attr("title");
            String sourceUrl = sourceElts.attr("href");

            String sourceSupplement = null;

            switch (sourceType) {
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

            ZHMember source = new ZHMember();
            source.setName(sourceName);
            source.setUrl(fixURL(sourceUrl));


            mFeed.setSourceType(sourceType);
            mFeed.setSource(source);
            mFeed.setSourceSupplement(sourceSupplement);
            mFeed.setSourceAvatarUrl(sourceAvatarUrl);

            return this;
        }

        public Builder setHead() {

            Elements contentElts = mElt.select("div[class=content]");
            Elements titleElts = contentElts.select("h2>a");

            String title = titleElts.text();
            String titleUrl = titleElts.attr("href");

            mFeed.setTitle(title);
            mFeed.setTitleUrl(fixURL(titleUrl));

            return this;
        }

        public Builder setContent() {

            Elements contentElts = mElt.select("div[class=content]");

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

            mFeed.setVoteupCount(mVoteups);
            mFeed.setSummary(summary);
            mFeed.setContentUrl(fixURL(contentUrl));


            if ("a".equals(mFeed.getFeedType())) {

                ZHAnswer zhAnswer = new ZHAnswer();
                ZHMember author = new ZHMember();

                Elements votebarElts = contentElts.select("div[class=zm-votebar]>button");

                boolean isVoteUp = false;
                boolean isVoteDown = false;
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

                if (mFeed.getSourceType().equals(HttpConstants.ANSWER_MEMBER)) {
                    authorUrl = mFeed.getSource().getUrl();
                    authorName = mFeed.getSource().getName();
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

                author.setName(authorName);
                author.setUrl(fixURL(authorUrl));
                author.setHeadline(authorHeadline);

                zhAnswer.setSummary(summary);
                zhAnswer.setUrl(fixURL(contentUrl));
                zhAnswer.setVoteupCount(mVoteups);
                zhAnswer.setCommentCount(mComments);
                zhAnswer.setIsVoteUp(isVoteUp);
                zhAnswer.setIsVoteDown(isVoteDown);
                zhAnswer.setIsNoHelped(isNoHelped);
                zhAnswer.setIsThanked(isThanked);
                zhAnswer.setAuthor(author);
                zhAnswer.setQuestion(mFeed.getTitle());

                mFeed.setZHAnswer(zhAnswer);

            }


            return this;
        }

        public ZHFeed create() {
            LogUtils.d(TAG, "========");
            LogUtils.d(TAG, mFeed.toString());
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
