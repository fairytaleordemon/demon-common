package com.yuanbosu.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClients {

	private static final CloseableHttpClient httpClient = config(Integer.valueOf(60000), Integer.valueOf(15000));
	public static final String CHARSET = "UTF-8";

	public static CloseableHttpClient config(Integer connectTimeout, Integer socketTimeout) {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout.intValue())
				.setSocketTimeout(socketTimeout.intValue()).build();
		return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	public static String doGet(String url, Map<String, String> params)
			throws ParseException, UnsupportedEncodingException, IOException {
		return doGet(httpClient, url, params, "UTF-8");
	}

	public static String doPost(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		return doPost(httpClient, url, params, "UTF-8");
	}

	public static String doGet(CloseableHttpClient client, String url, Map<String, String> params, String charset)
			throws ParseException, UnsupportedEncodingException, IOException {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		if ((params != null) && (!params.isEmpty())) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String,String> entry : params.entrySet()) {
				String value = (String) entry.getValue();
				if (value != null) {
					pairs.add(new BasicNameValuePair((String) entry.getKey(), value));
				}
			}
			url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
		}
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = client.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			httpGet.abort();
			throw new RuntimeException("HttpClient,error status code :" + statusCode);
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		response.close();
		return result;
	}

	public static String doPost(CloseableHttpClient client, String url, Map<String, String> params, String charset)
			throws ClientProtocolException, IOException {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		List<NameValuePair> pairs = null;
		if ((params != null) && (!params.isEmpty())) {
			pairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String,String> entry : params.entrySet()) {
				String value = (String) entry.getValue();
				if (value != null) {
					pairs.add(new BasicNameValuePair((String) entry.getKey(), value));
				}
			}
		}
		HttpPost httpPost = new HttpPost(url);
		if ((pairs != null) && (pairs.size() > 0)) {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
		}
		CloseableHttpResponse response = client.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			httpPost.abort();
			throw new RuntimeException("HttpClient,error status code :" + statusCode);
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
		}
		EntityUtils.consume(entity);
		response.close();
		return result;
	}

	// change by suyuanb
	public static CloseableHttpClient createDefault() {

		return httpClient;
	}

	public static void main(String[] args) {
		String url = "http://116.213.72.20/SMSHttpService/send.aspx";

		System.out.println("----------------------分割线-----------------------");

		String postData = null;
		try {
			Map<String,String> content = new HashMap<String,String>();
			content.put("username", "dsty");
			content.put("password", "desty");
			content.put("content", "您的验证码是：12345，请无泄漏，10分钟内有效。[德赛网]");
			content.put("mobile", "18601661158");

			postData = doPost(url, content);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(postData);
	}
}
