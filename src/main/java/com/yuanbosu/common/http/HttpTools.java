package com.yuanbosu.common.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class HttpTools {

	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if ((StringUtils.isNotEmpty(ip)) && (!"unKnown".equalsIgnoreCase(ip))) {
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			}
			return ip;
		}

		ip = request.getHeader("X-Real-IP");
		if ((StringUtils.isNotEmpty(ip)) && (!"unKnown".equalsIgnoreCase(ip))) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
