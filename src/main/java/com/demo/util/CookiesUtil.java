package com.demo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtil {
    public static String getCookieByName(HttpServletRequest req,String cookiename) {
        Cookie []cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookiename.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
    
    public static void saveCookie(HttpServletResponse resp,String cookieName,String cookieValue) {
        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setMaxAge(60 * 60 * 24 * 7 * 1);
        cookie.setPath("/");
        resp.addCookie(cookie);   
    }
    
    public static void removeCookie(HttpServletResponse resp,String cookieName) {
        Cookie cookie = new Cookie(cookieName,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie); 
    }
}