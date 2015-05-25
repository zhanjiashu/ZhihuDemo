package com.jiashu.zhihudemo.utils;

import android.text.TextUtils;

import com.jiashu.zhihudemo.mode.ZHAnswer;
import com.jiashu.zhihudemo.mode.ZHFeed;
import com.jiashu.zhihudemo.mode.ZHMember;
import com.jiashu.zhihudemo.mode.ZHQuestion;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/22.
 */
public class ParseUtils {

    private static final String TAG = "ParseUtils";

    public static ZHQuestion parseHtmlToQuestion(String html) {
        ZHQuestion question = new ZHQuestion();

        Document doc = Jsoup.parse(html);

        String urlToken = doc.select("div[class=zg-wrap zu-main question-page]").attr("data-urltoken");
        String url = "http://www.zhihu.com/question/"+urlToken;

        String title = doc.select("h2[class=zm-item-title zm-editable-content]").text();
        String descripition = doc.select("div[id=zh-question-detail]>div[class=zm-editable-content]").html();
        String answerCountStr = doc.select("div[class=zh-answers-title clearfix]>h3[id=zh-question-answer-num]").attr("data-num");
        int answerCount = 0;
        if (!TextUtils.isEmpty(answerCountStr)) {
            answerCount = Integer.valueOf(answerCountStr);
        }

        Elements answerElts = doc.select("div[class=zm-item-answer ]");
        List<ZHAnswer> answerList = new ArrayList<>();

        for (Element element : answerElts) {
            ZHAnswer answer = parseHtmlToAnswer(title, url, element);
            answerList.add(answer);
        }

        String followCountStr = doc.select("div[class=zh-question-followers-sidebar]>div>a>strong").text();
        String commentCountStr = doc.select("div[id=zh-question-meta-wrap]>div>a[name=addcomment]").text().replace(" 条评论", "");

        question.setTitle(title);
        question.setUrl(url);
        question.setDescription(descripition);
        question.setAnswerCount(answerCount);
        question.setAnswers(answerList);
        question.setFollows(getCommentCount(followCountStr));
        question.setComments(getCommentCount(commentCountStr));
        return question;
    }

    public static ZHAnswer parseHtmlToAnswer(String questionTitle, String questionUrl, Element element) {

        ZHAnswer answer = new ZHAnswer();
        ZHMember author = new ZHMember();

        Elements votebarElts = element.select("div[class=zm-votebar]");

        String voteupCountStr = votebarElts.select("span[class=count]").text();
        int voteupCount = 0;
        if (!TextUtils.isEmpty(voteupCountStr)) {

            if (voteupCountStr.endsWith("K")) {
                voteupCountStr = voteupCountStr.replace("K", "000");
            }

            voteupCount = Integer.valueOf(voteupCountStr);
        }

        Elements voteBtnElts = votebarElts.select("button");
        boolean isVoteUp = false;
        boolean isVoteDown = false;
        if (voteBtnElts.size() >= 2) {
            if ("up pressed".equals(voteBtnElts.get(0).attr("class"))) {
                isVoteUp = true;
            } else if ("down pressed".equals(voteBtnElts.get(1).attr("class"))) {
                isVoteDown = true;
            }
        }

        Elements authorElts = element.select("h3[class=zm-item-answer-author-wrap]");
        String authorUrl = authorElts.select("a").attr("href");
        String avatarUrl = authorElts.select("img[class=zm-list-avatar]").attr("src").replace("_s", "_l");
        String authorName = authorElts.text().split("，")[0];
        String authorHeadline = authorElts.select("strong").text();

 /*           String avatarId = avatarUrl.substring(avatarUrl.lastIndexOf("/"), avatarUrl.lastIndexOf("_"));
            String avatarSuf = avatarUrl.substring(avatarUrl.lastIndexOf("/"), avatarUrl.lastIndexOf("_s"));
            String avatarTemplate = avatarUrl.replace(avatarSuf, "{id}_{size}");
            avatar.setId(avatarId);
            avatar.setTemplate(avatarTemplate);

            author.setName(authorName);
            author.setProfileUrl(authorUrl);
            author.setBio(authorHeadline);
            author.setAvatar(avatar);*/

        author.setUrl(authorUrl);
        author.setHeadline(authorHeadline);
        author.setName(authorName);
        author.setAvatarUrl(avatarUrl);


        String answerTime = element.attr("data-created").trim();
        String answerContent = element.select("div[class= zm-editable-content clearfix]").html();
        String answerSummary = element.select("div[class= zm-editable-content clearfix]").text();
        String answerTimePre = element.select("span[class=answer-date-link-wrap]>a").attr("class");

        if (TextUtils.isEmpty(answerTime)) {
            answerTime = String.valueOf(System.currentTimeMillis() / 1000);
        }

        Date date = new Date(Long.valueOf(answerTime) * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        answerTime = format.format(date);

        if (answerTimePre.equals("answer-date-link last_updated meta-item")) {
            answerTime = "编辑于 " + answerTime;
        } else {
            answerTime = "创建于 " + answerTime;
        }

        answerTime = "<br>\n" +
                "<br>\n" +
                "<span style=\"float:right\" class=\""+ answerTimePre +"\">"+ answerTime +"</span>";

        answerContent = answerContent + answerTime;


        if (TextUtils.isEmpty(answerSummary)) {
            answerSummary = "[图片]";
        } else if (answerSummary.length() > 60) {
            answerSummary = answerSummary.substring(0, 60) + "...";
        }


        Elements panelElts = element.select("div[class=zm-meta-panel]");

        String commentStr = panelElts.select("a[name=addcomment]").text().replace(" 条评论", "");
        String thanks = panelElts.select("a[name=thanks]").attr("data-thanked");
        String nohelp = panelElts.select("a[name=nohelp]").attr("data-revert");
        LogUtils.d(TAG, commentStr);

        boolean isThanked = false;
        boolean isNoHelped = false;

        if (thanks != null && "true".equals(thanks)) {
            isThanked = true;
        }

        if (nohelp != null && "true".equals(nohelp)) {
            isNoHelped = true;
        }

        answer.setQuestionTitle(questionTitle);
        answer.setQUestionUrl(questionUrl);
        answer.setIsVoteUp(isVoteUp);
        answer.setIsVoteDown(isVoteDown);
        answer.setVoteupCount(voteupCount);
        answer.setContent(answerContent);
        answer.setSummary(answerSummary);
        answer.setCommentCount(getCommentCount(commentStr));
        answer.setIsThanked(isThanked);
        answer.setIsNoHelped(isNoHelped);
        answer.setAuthor(author);
        return answer;
    }

    /**
     * 通过 Jsoup 解析响应的 html,并获取 【知乎】首页的 Feed 信息流
     * @param html
     * @return
     */
    public static List<ZHFeed> parseHtmlToFeedList(String html) {
        List<ZHFeed> feedList = new ArrayList<>();
        Document doc = Jsoup.parse(html);

        String homePageDataInit = doc.select("div[id=js-home-feed-list]").attr("data-init");

        String nodeName = "";
        try {
            JSONObject homepageJson = new JSONObject(homePageDataInit);

            nodeName = homepageJson.getString("nodename");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.setNodeName(nodeName);

        // 遍历返回数据中 包含着 Feed 的 div
        Elements elements = doc.select("div[id^=feed]");
        for (Element element : elements) {

            ZHFeed feed = new ZHFeed.Builder(element).create();

            feedList.add(feed);
        }
        return feedList;
    }

    private static int getCommentCount(String str) {
        int temp = 0;
        if (!TextUtils.isEmpty(str) && !"添加评论".equals(str)) {
            temp = Integer.valueOf(str);
        }
        return temp;
    }
}
