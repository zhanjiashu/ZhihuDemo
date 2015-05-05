package com.jiashu.zhihudemo.data;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.ZhiHuApp;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class NetConstants {
    public static final String HOST = "http://www.zhihu.com";
    public static final String LOGIN_URL = HOST + "/login";
    public static final String CAPTCHA_URL = HOST + "/captcha.gif";

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAIL = 1;

    public static final int ERRCODE_PWD_EMAIL_ERROR = 4038;
    public static final int ERRCODE_EMAIL_FORMAT_ERROR = 4003;
    public static final int ERRCODE_PWD_LENGTH_ERROR = 4000;
    public static final int ERRCODE_INPUT_CAPTCHA = 269;

    public static final String ERRMSG_PWD_EMAIL_ERROR = getString(R.string.toast_login_error_default);
    public static final String ERRMSG_EMAIL_FORMAT_ERROR = getString(R.string.toast_login_error_email);
    public static final String ERRMSG_PWD_LENGTH_ERROR = getString(R.string.toast_login_error_pwd);
    public static final String ERRMSG__INPUT_CAPTCHA = getString(R.string.toast_login_error_captcha);

    // ZhiHuFeed 类型：  回答、问题、文章

    // [热门问答]
    public static final String ANSWER_PROMOTION = "promotion_answer";
    // 来自某个话题的问答
    public static final String ANSWER_TOPIC = "topic_acknowledged_answer";
    // 某个被关注者 赞同的问答
    public static final String ANSWER_MEMBER_VOTEUP = "member_voteup_answer";
    // 某个被关注者 的问答
    public static final String ANSWER_MEMBER_QUESTION = "member_answer_question";

    // 来自某个话题的热门问题
    public static final String QUESTION_TOPIC = "topic_popular_question";
    // 某个被关注者 关注的问题
    public static final String QUESTION_MEMBER_FOLLOW = "member_follow_question";

    // 某个被关注者 关注了的专栏
    public static final String COLUMN_MEMBER_FOLLOW  ="member_follow_column";

    // 某个被关注者 发表的专栏文章
    public static final String ARTICLE_MEMBER_CREATE = "member_create_article";
    // 某个被关注者 赞同的专栏文章
    public static final String ARTICLE_MEMBER_VOTEUP = "member_voteup_article";


    public static String getString(int resId) {
        return ZhiHuApp.getContext().getResources().getString(resId);
    }
}
