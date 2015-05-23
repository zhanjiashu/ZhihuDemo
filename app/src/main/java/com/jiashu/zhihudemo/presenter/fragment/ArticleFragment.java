package com.jiashu.zhihudemo.presenter.fragment;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.CompoundButton;

import com.jiashu.zhihudemo.app.ZHApp;
import com.jiashu.zhihudemo.data.HttpConstants;
import com.jiashu.zhihudemo.events.http.FetchArticleHRE;
import com.jiashu.zhihudemo.events.http.HttpResponseEvent;
import com.jiashu.zhihudemo.mode.ZHArticle;
import com.jiashu.zhihudemo.other.ZHScrollView;
import com.jiashu.zhihudemo.presenter.activity.FrameActivity;
import com.jiashu.zhihudemo.task.FetchArticleTask;
import com.jiashu.zhihudemo.task.FetchXSRFTask;
import com.jiashu.zhihudemo.task.HttpTask;
import com.jiashu.zhihudemo.utils.HttpUtils;
import com.jiashu.zhihudemo.vu.ArticleVu;

import java.text.MessageFormat;

/**
 * Created by Jiashu on 2015/5/23.
 * 【文章】 的 内容详情页面
 */
public class ArticleFragment extends BasePresenterFragment<ArticleVu> {

    private final String TAG = getClass().getName();

    private static final String EXTRA_ARTICLE_URL = "articleUrl";

    private static final int HEADER_HIGHT = 240;

    private ActionBar mActionBar;
    private Drawable mActionBarDrawable;

    private int mVoteupsCount;

    private boolean mHasTilteImg;

    private HttpTask mTask;

    @Override
    protected void onBindVu() {

        mBus.register(this);

        initActionBar();

        ZHArticle article = getArguments().getParcelable(FrameActivity.EXTRA_PARAM);
        String articleUrl = article.getUrl();

        mTask = new FetchArticleTask(getActivity(), articleUrl);
        HttpUtils.executeTask(mTask);

        handleScroll();

        handleVote();
    }

    @Override
    protected void beforePause() {
        super.beforePause();
        HttpUtils.cancelTask(mTask);

        // 获取 Article 的API会引入改变 cookie, 这将导致 底部上拉加载数据失败
        // 通过重新获取新的 _xsrf，保持持有正确的 cookie 以及 最新的 _xsrf 参数。
        FetchXSRFTask task = new FetchXSRFTask();
        HttpUtils.executeTask(task);
    }

    @Override
    protected void onDestroyVu() {
        mBus.unregister(this);
    }

    @Override
    protected Class<ArticleVu> getVuClass() {
        return ArticleVu.class;
    }

    /**
     * 初始化 ActionBar
     */
    private void initActionBar() {
        mActionBarDrawable = mVu.getToobar().getBackground();
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.setSupportActionBar(mVu.getToobar());
        mActionBar = activity.getSupportActionBar();
        mActionBar.setBackgroundDrawable(mActionBarDrawable);
        mActionBar.setTitle("专栏");
    }


    /**
     * 处理 滚动 事件
     */
    private void handleScroll() {
        mVu.setScrollLisener(new ZHScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

                int headHeight = dp2px(HEADER_HIGHT);

                // 动态设置 Toolbar 背景的透明度、以及移动 titleImg
                if (mHasTilteImg && t <= headHeight) {
                    float ratio = (float) Math.min(Math.max(t, 0), headHeight) / headHeight;
                    int newAlpha = (int) (ratio * 255);
                    mActionBarDrawable.setAlpha(newAlpha);
                    mVu.moveTitleImage(-t * Math.min(ratio, 0.5f));
                }
            }
        });
    }

    /**
     * 处理 投票 事件
     */
    private void handleVote() {
        // 监听 “赞同” 按钮点击事件
        mVu.setVoteUpListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mVu.setVoteDownBtn(false);
                mVu.setVoteUpBtn(isChecked, mVoteupsCount);
            }
        });

        // 监听 “反对” 按钮点击事件
        mVu.setVoteDownListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mVu.setVoteUpBtn(false, mVoteupsCount);
                mVu.setVoteDownBtn(isChecked);
            }
        });
    }

    // 显示文章
    private void setArticle(ZHArticle article) {
        // 显示 专栏名字
        mActionBar.setTitle(article.getColumn().getName());
        // 显示文字标题
        mVu.setTitle(article.getTitle());

        String titleImgUrl = article.getTitleImage();

        // 判断是否 顶部图片：
        //  如果有则设置 Toolbar透明并加载图片
        //  如果没有则隐藏顶部区域
        if (!TextUtils.isEmpty(titleImgUrl)) {
            mActionBarDrawable.setAlpha(0);
            mHasTilteImg = true;
            mVu.setTitleImg(titleImgUrl, mVolleyUtils.getImageLoader());
        } else {
            mVu.dimissHead();
        }

        // 显示作者名称
        mVu.setAuthorName(article.getAuthor().getName());

        // 显示作者头像
        String avatarTemplate = "http://pic1.zhimg.com/{0}_{1}.jpg";
        String avatarId= article.getAuthor().getAvatar().getId();

        String avatarImgUrl = MessageFormat.format(avatarTemplate, avatarId, "s");

        mVu.setAuthorAvatar(avatarImgUrl, mVolleyUtils.getImageLoader());

        // 显示文章内容
        String articleContent = HttpConstants.ANSWER_HTML_PRE + article.getContent() + "<br><br>" + HttpConstants.ANSWER_HTML_SUF;
        mVu.setContent(articleContent);

        // 设置底部区域
        String rating = article.getRating();

        boolean isVoteUped = "like".equals(article.getRating());
        boolean isVoteDowned = "dislike".equals(article.getRating());

        mVoteupsCount = article.getLikesCount();

        if (isVoteUped) {
            mVoteupsCount = mVoteupsCount - 1;
        }

        mVu.setVoteUpBtn(isVoteUped, mVoteupsCount);

        mVu.setVoteDownBtn(isVoteDowned);

        mVu.setCommentBtn(article.getCommentsCount());

    }

    /**
     * dp 转化为 px
     * @param dp
     * @return
     */
    private int dp2px(float dp) {
        final float scale = ZHApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    // 获取 文章 成功时触发
    public void onEvent(HttpResponseEvent event) {
        if (event instanceof FetchArticleHRE) {
            ZHArticle article = (ZHArticle) event.data;
            setArticle(article);
        }
    }
}
