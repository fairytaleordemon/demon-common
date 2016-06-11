package com.yuanbosu.common.error;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.Gson;

public class ErrorResponse {

	private String userId;
	  private String userName;
	  private Integer status;
	  private String error;
	  private String exception;
	  private String message;
	  private String url;
	  private String parameters;

	  public ErrorResponse()
	  {
	  }

	  public ErrorResponse(HttpServletRequest request, Integer status, String message)
	  {
	    this.url = initUrl(request);
	    this.parameters = initParameters(request);
	    this.status = status;
	    this.message = message;
	  }

	  public ErrorResponse(HttpServletRequest request, Throwable throwable)
	  {
	    this.url = initUrl(request);
	    this.parameters = initParameters(request);

	    this.message = StringUtils.substringAfter(throwable.getMessage(), ":");
	    this.exception = ExceptionUtils.getStackTrace(throwable);
	  }

	  public Integer getStatus()
	  {
	    return this.status;
	  }

	  public void setStatus(Integer status) {
	    this.status = status;
	  }

	  public String getError() {
	    return this.error;
	  }

	  public void setError(String error) {
	    this.error = error;
	  }

	  public String getException() {
	    return this.exception;
	  }

	  public void setException(String exception) {
	    this.exception = exception;
	  }

	  public String getMessage() {
	    return this.message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	  public String getUrl() {
	    return this.url;
	  }

	  public void setUrl(String url) {
	    this.url = url;
	  }

	  public String getParameters() {
	    return this.parameters;
	  }

	  public void setParameters(String parameters) {
	    this.parameters = parameters;
	  }

	  public String getUserId() {
	    return this.userId;
	  }

	  public void setUserId(String userId) {
	    this.userId = userId;
	  }

	  public String getUserName() {
	    return this.userName;
	  }

	  public void setUserName(String userName) {
	    this.userName = userName;
	  }

	  public String toJsonString()
	  {
	    setException(null);
	    setParameters(null);
	    return new Gson().toJson(this);
	  }

	  private String initUrl(HttpServletRequest request)
	  {
	    String queryString = StringUtils.trimToEmpty(request.getQueryString());
	    return request.getRequestURI() + "?" + queryString;
	  }

	  private String initParameters(HttpServletRequest request)
	  {
	    Map<String,String[]> parameters = request.getParameterMap();
	    StringBuffer paramString = new StringBuffer();

	    paramString.append("[");
	    String separate = "";
	    for (Map.Entry<String,String[]> entry : parameters.entrySet()) {
	      String key = ((String)entry.getKey()).toString();

	      paramString.append(separate);
	      paramString.append(key);
	      paramString.append("=");

	      String[] value = (String[])entry.getValue();
	      if ((value == null) || (value.length < 1))
	      {
	        continue;
	      }
	      String separate2 = "";
	      for (String val : value) {
	        paramString.append(separate2);
	        paramString.append(val);
	        separate2 = "|";
	      }

	      separate = ",";
	    }

	    paramString.append("]");
	    return paramString.toString();
	  }

	  public String toString()
	  {
	    return "userId=" + this.userId + ", userName=" + this.userName + ", status=" + this.status + ", error=" + this.error + ", exception=" + this.exception + ", message=" + this.message + ", url=" + this.url + ", parameters=" + this.parameters;
	  }
}
