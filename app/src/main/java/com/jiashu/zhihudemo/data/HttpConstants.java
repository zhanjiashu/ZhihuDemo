package com.jiashu.zhihudemo.data;

import com.jiashu.zhihudemo.R;
import com.jiashu.zhihudemo.app.ZHApp;

/**
 * Created by Jiashu on 2015/5/3.
 */
public class HttpConstants {

    public static final String UA = "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";

    public static final String HOST = "http://www.zhihu.com";
    public static final String LOGIN_URL = HOST + "/login";
    public static final String CAPTCHA_URL = HOST + "/captcha.gif";
    public static final String LOADING_URL_HOME = HOST + "/node/HomeFeedListV2";
    public static final String LOADING_URL_TOP_STORY = HOST + "/node/TopStory2FeedList";

    public static final String NODE_NAME_HOME = "HomeFeedListV2";
    public static final String NODE_NAME_TOP_STORY = "TopStory2FeedList";

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

    // ZHFeed 类型：  回答、问题、文章

    // [热门问答]
    public static final String ANSWER_PROMOTION = "promotion_answer";
    // 来自某个话题的回答
    public static final String ANSWER_TOPIC = "topic_acknowledged_answer";
    // 某个被关注者 赞同的回答
    public static final String ANSWER_MEMBER_VOTEUP = "member_voteup_answer";
    // 某个被关注者 的回答
    public static final String ANSWER_MEMBER = "member_answer_question";

    // 来自某个话题的热门问题
    public static final String QUESTION_TOPIC = "topic_popular_question";
    // 某个被关注者 关注的问题
    public static final String QUESTION_MEMBER_FOLLOW = "member_follow_question";
    // 提问
    public static final String QUESTION_MEMBER_ASK = "member_ask_question";

    // 某个被关注者 关注了的专栏
    public static final String COLUMN_MEMBER_FOLLOW  ="member_follow_column";

    // 某个被关注者 发表的专栏文章
    public static final String ARTICLE_MEMBER_CREATE = "member_create_article";
    // 某个被关注者 赞同的专栏文章
    public static final String ARTICLE_MEMBER_VOTEUP = "member_voteup_article";
    // 某个专栏 发表的文章
    public static final String ARTICLE_COLUMN_CREATE = "column_new_article";

    // 关注了圆桌
    public static final String ROUNDTABLE_MEMBER_FOLLOW = "member_follow_roundtable";


    public static String getString(int resId) {
        return ZHApp.getContext().getResources().getString(resId);
    }

    /*public static final String ANSWER_HTML_PRE = "<!DOCTYPE html><html lang=\"zh-CN\" dropEffect=\"none\" class=\"no-js \"><head><meta charset=\"utf-8\" /><title>首页 - 知乎</title><meta name=\"apple-itunes-app\" content=\"app-id=432274380\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\"/><meta http-equiv=\"mobile-agent\" content=\"format=html5;url=http://www.zhihu.com/\"><meta id=\"znonce\" name=\"znonce\" content=\"778601aae7d84eafabe978b10b96ce94\"><link rel=\"stylesheet\" href=\"http://static.zhihu.com/static/ver/ff9b5bb7dec09eb7dce17116342f4d3b.m.css\"><script src=\"http://static.zhihu.com/static/ver/30ff61b37d19820b83181a78d6ca0d16.instant.min.js\"></script><meta property=\"wb:webmaster\" content=\"8e9c4b702508b902\" /></head><body class=\"zhi\">";
    public static final String ANSWER_HTML_SUF = "</body></html>";*/
    public static final String ANSWER_HTML_PRE = "<!DOCTYPE html>\n" +
            "<html lang=\"zh-CN\" dropEffect=\"none\" class=\"no-js\">\n" +
            "<head>\n" +
            "\n" +
            "<link rel=\"stylesheet\" href=\"http://static.zhihu.com/static/ver/d6d1d8af46437d8edc24966ce70eafdd.z.css\">\n" +
            "\n" +
            "<link rel=\"canonical\" href=\"http://www.zhihu.com/question/29246053\" />\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body class=\"zhi\">\n" +
            "\n" +
            "<div class=\"zg-wrap zu-main\" id=\"zh-single-answer-page\">\n" +
            "<div class=\"zu-main-content\">\n" +
            "<div class=\"zu-main-content-inner with-indention-votebar\">";

    public static final String ANSWER_HTML_SUF = "</div>\n" +
            "</div>\n" +
            "\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
}
