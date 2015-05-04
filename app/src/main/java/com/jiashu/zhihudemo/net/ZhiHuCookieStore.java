package com.jiashu.zhihudemo.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jiashu.zhihudemo.ZhiHuApp;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持久化 Cookie
 * Created by Jiashu on 2015/5/3.
 */
public class ZhiHuCookieStore implements CookieStore {

    private static final String COOKIE_FILE_NAME = "ZhiHuCookies";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private SharedPreferences mPref;
    private ConcurrentHashMap<String, Cookie> mCookies;

    public ZhiHuCookieStore() {
        mPref = ZhiHuApp.getContext().getSharedPreferences(COOKIE_FILE_NAME, Context.MODE_PRIVATE);
        mCookies = new ConcurrentHashMap<>();

        String cookieNamesStr = mPref.getString(COOKIE_NAME_STORE, null);
        if (!TextUtils.isEmpty(cookieNamesStr)) {
            String[] cookieNames = TextUtils.split(cookieNamesStr, ",");
            for (String name : cookieNames) {
                String encodedCookie = mPref.getString(COOKIE_NAME_PREFIX + name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        mCookies.put(name, decodedCookie);
                    }
                }
            }
            clearExpired(new Date());
        }
    }

    @Override
    public void addCookie(Cookie cookie) {
        if (!cookie.isPersistent()) {
            return;
        }

        String name = cookie.getName();
        if (!cookie.isExpired(new Date())) {
            mCookies.put(name, cookie);
        } else {
            mCookies.remove(name);
        }

        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(COOKIE_NAME_STORE, TextUtils.join(",", mCookies.keySet()));
        editor.putString(COOKIE_NAME_PREFIX + name, encodeCookie(new SerializableCookie(cookie)));
        editor.commit();

    }


    @Override
    public List<Cookie> getCookies() {
        return new ArrayList<Cookie>(mCookies.values());
    }

    @Override
    public boolean clearExpired(Date date) {
        boolean clearedAny = false;
        SharedPreferences.Editor editor = mPref.edit();

        for (ConcurrentHashMap.Entry<String, Cookie> entry : mCookies.entrySet()) {
            String name = entry.getKey();
            Cookie cookie = entry.getValue();
            if (cookie.isExpired(date)) {
                mCookies.remove(name);
                editor.remove(COOKIE_NAME_PREFIX + name);
                clearedAny = true;
            }
        }

        if (clearedAny) {
            editor.putString(COOKIE_NAME_STORE, TextUtils.join(",", mCookies.keySet()));
        }
        editor.commit();

        return clearedAny;
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = mPref.edit();
        for (String name : mCookies.keySet()) {
            editor.remove(COOKIE_NAME_PREFIX + name);
        }
        editor.remove(COOKIE_NAME_STORE);
        editor.commit();

        mCookies.clear();
    }

    private String encodeCookie(SerializableCookie serializableCookie) {
        if (serializableCookie == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOS = new ObjectOutputStream(os);
            objectOS.writeObject(serializableCookie);
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    private Cookie decodeCookie(String cookieStr) {
        byte[] bytes = hexStringToByteArray(cookieStr);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie) objectInputStream.readObject()).getCookie();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public class SerializableCookie implements Serializable {
        private static final long serialVersionUID = 6374381828722046732L;

        private transient final Cookie cookie;
        private transient BasicClientCookie clientCookie;

        public SerializableCookie(Cookie cookie) {
            this.cookie = cookie;
        }

        public Cookie getCookie() {
            Cookie bestCookie = cookie;
            if (clientCookie != null) {
                bestCookie = clientCookie;
            }
            return bestCookie;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(cookie.getName());
            out.writeObject(cookie.getValue());
            out.writeObject(cookie.getComment());
            out.writeObject(cookie.getDomain());
            out.writeObject(cookie.getExpiryDate());
            out.writeObject(cookie.getPath());
            out.writeInt(cookie.getVersion());
            out.writeBoolean(cookie.isSecure());
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            String name = (String) in.readObject();
            String value = (String) in.readObject();
            clientCookie = new BasicClientCookie(name, value);
            clientCookie.setComment((String) in.readObject());
            clientCookie.setDomain((String) in.readObject());
            clientCookie.setExpiryDate((Date) in.readObject());
            clientCookie.setPath((String) in.readObject());
            clientCookie.setVersion(in.readInt());
            clientCookie.setSecure(in.readBoolean());
        }
    }
}
