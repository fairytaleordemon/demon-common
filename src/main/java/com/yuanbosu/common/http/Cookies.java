package com.yuanbosu.common.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;


public class Cookies {

	public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String key, String value)
	  {
	    saveCookie(request, response, key, value, "/", Integer.valueOf(-1));
	  }

	  public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String domain, String key, String value) {
	    saveCookie(request, response, domain, key, value, "/", Integer.valueOf(-1));
	  }

	  public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String key, String value, String path, Integer MaxAge) {
	    saveCookie(request, response, (String)null, key, value, path, MaxAge);
	  }

	  public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String domain, String key, String value, String path, Integer MaxAge)
	  {
	    Cookie cookie = getCookie(request, key);
	    if (cookie == null) cookie = new Cookie(key, value);
	    if (domain != null) cookie.setDomain(domain);
	    cookie.setValue(value);
	    cookie.setPath(path);
	    if (MaxAge.intValue() > 0) cookie.setMaxAge(MaxAge.intValue());
	    response.addCookie(cookie);
	  }

	  public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String domain)
	  {
	    Cookie[] cookies = request.getCookies();
	    if (cookies == null) return;
	    for (Cookie ck : cookies)
	    {
	      removeCookie(response, domain, ck.getName());
	    }
	  }

	  public static String[] getCookieValue(HttpServletRequest request, String[] key)
	  {
	    Cookie[] cookies = request.getCookies();
	    String[] value = new String[key.length];

	    if (cookies == null) return null;

	    for (Cookie cookie : cookies) {
	      int i = 0;
	      for (String s : key) {
	        if (StringUtils.equals(s, cookie.getName())) {
	          value[i] = cookie.getValue();
	          break;
	        }
	        i++;
	      }
	    }
	    return value;
	  }

	  public static Cookie getCookie(HttpServletRequest request, String key)
	  {
	    Cookie[] cookies = request.getCookies();
	    if (cookies == null) return null;

	    for (Cookie cookie : cookies) {
	      if (StringUtils.equals(key, cookie.getName())) {
	        return cookie;
	      }
	    }

	    return null;
	  }

	  public static String getCookieValue(HttpServletRequest request, String key) {
	    Cookie cookie = getCookie(request, key);
	    if (cookie == null) return null;

	    if ((cookie.getValue() == null) || (cookie.getMaxAge() == 0)) return null;

	    return StringUtils.trimToNull(cookie.getValue());
	  }

	  public static void removeCookie(HttpServletResponse response, String key)
	  {
	    removeCookie(response, null, key);
	  }

	  public static void removeCookie(HttpServletResponse response, String domain, String key) {
	    Cookie cookie = new Cookie(key, null);
	    if (domain != null) cookie.setDomain(domain);
	    cookie.setMaxAge(0);
	    cookie.setPath("/");
	    response.addCookie(cookie);
	  }
}
