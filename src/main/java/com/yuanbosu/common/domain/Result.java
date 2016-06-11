package com.yuanbosu.common.domain;

import com.google.gson.Gson;

public class Result<T> {


	  private int code;
	  private String message;
	  private T data;

	  public Result(int code)
	  {
	    this.code = code;
	  }

	  public Result(int code, String message)
	  {
	    this.code = code;
	    this.message = message;
	  }

	  public Result(int code, T data)
	  {
	    this.code = code;
	    this.data = data;
	  }

	  public Result(int code, String message, T data)
	  {
	    this.code = code;
	    this.message = message;
	    this.data = data;
	  }

	  public String getMessage() {
	    return this.message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	  public int getCode() {
	    return this.code;
	  }
	  public void setCode(int code) {
	    this.code = code;
	  }
	  public T getData() {
	    return this.data;
	  }
	  public void setData(T data) {
	    this.data = data;
	  }

	  public String toJson() {
	    return new Gson().toJson(this);
	  }
}
