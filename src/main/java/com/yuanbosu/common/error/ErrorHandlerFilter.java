package com.yuanbosu.common.error;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.yuanbosu.common.domain.Result;

public class ErrorHandlerFilter implements Filter
{
	  public void init(FilterConfig filterConfig)
	    throws ServletException
	  {
	  }

	  public void destroy()
	  {
	  }

	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException
	  {
	    try
	    {
	      chain.doFilter(request, response);
	    }
	    catch (Exception t) {
	      HttpServletResponse resp = (HttpServletResponse)response;
	      resp.setStatus(500);

	      resp.getOutputStream().write(new Result<Exception>(500, t.getMessage(), t).toJson().getBytes());
	    }
	  }
	
}
