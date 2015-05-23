package com.jiashu.zhihudemo.utils;

import android.text.TextUtils;

import com.jiashu.zhihudemo.mode.ZHAnswer;
import com.jiashu.zhihudemo.mode.ZHAuthor;
import com.jiashu.zhihudemo.mode.ZHAvatar;
import com.jiashu.zhihudemo.mode.ZHMember;
import com.jiashu.zhihudemo.mode.ZHQuestion;

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

    public static ZHQuestion parseHtmlToQuestion(String html) {
        ZHQuestion question = new ZHQuestion();

        Document doc = Jsoup.parse(html);

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
            ZHAnswer answer = new ZHAnswer();
            /*ZHAuthor author = new ZHAuthor();*/
            ZHMember author = new ZHMember();
            ZHAvatar avatar = new ZHAvatar();

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

            String commentStr = panelElts.select("a[name=addcomment]>i").text();
            String thanks = panelElts.select("a[name=thanks]").attr("data-thanked");
            String nohelp = panelElts.select("a[name=nohelp]").attr("data-revert");

            commentStr = commentStr.replace(" 条评论", "");

            int comentCount = 0;
            if (!TextUtils.isEmpty(commentStr)) {
                comentCount = Integer.valueOf(commentStr);
            }

            boolean isThanked = false;
            boolean isNoHelped = false;

            if (thanks != null && "true".equals(thanks)) {
                isThanked = true;
            }

            if (nohelp != null && "true".equals(nohelp)) {
                isNoHelped = true;
            }

            answer.setQuestion(title);
            answer.setIsVoteUp(isVoteUp);
            answer.setIsVoteDown(isVoteDown);
            answer.setVoteupCount(voteupCount);
            answer.setContent(answerContent);
            answer.setSummary(answerSummary);
            answer.setCommentCount(comentCount);
            answer.setIsThanked(isThanked);
            answer.setIsNoHelped(isNoHelped);
            answer.setAuthor(author);
            answerList.add(answer);
        }

        question.setTitle(title);
        question.setDescription(descripition);
        question.setAnswerCount(answerCount);
        question.setAnswers(answerList);

        return question;
    }
}
