package com.jiashu.zhihudemo.net;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * 管理 cookie
 * Created by Jiashu on 2015/5/3.
 */
public class ZhiHuCookieManager {

    /**
     * 判断当前 CookieStore 是否包含某个 cookie
     * @param name
     * @return
     */
    public static boolean hasCookie(String name) {
        ZhiHuCookieStore cookieStore = new ZhiHuCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取某个 Cookie 的值
     * @param name
     * @return
     */
    public static String getCookieValue(String name) {
        ZhiHuCookieStore cookieStore = new ZhiHuCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void clearCookies() {
        new ZhiHuCookieStore().clear();
    }
}
