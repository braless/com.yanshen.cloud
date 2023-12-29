package com.yanshen.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.yanshen.exception.TipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;


import feign.FeignException;

public class ExceptionFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionFilter.class);
	
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, NestedServletException {
		try {
			chain.doFilter(request, response);
		} catch(NestedServletException e1) {
			Throwable e2 = e1.getCause();
			if(e2 instanceof FeignException) {
				FeignException e = (FeignException)e2;
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				PrintStream pw = new PrintStream(os);
				e.printStackTrace(pw);

				ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

				InputStreamReader isr = new InputStreamReader(is);
				
				BufferedReader r = new BufferedReader(isr);
				String json1 = r.readLine();
				String json2 = r.readLine();
				ObjectMapper mapper = new ObjectMapper();
				ExceptionInfo i = null;
				try {
					i = mapper.readValue(json2, ExceptionInfo.class);
				}catch(Exception ex) {
					LOG.info("ExceptionInfo:"+json1);
					LOG.info("ExceptionInfo:"+json2);
					throw e1;
				}
				
				if(i.getCode().startsWith("2")) {
					String s = "{\"code\":\"200000\",\"message\":\"" + URLDecoder.decode(i.getMessage(), "utf8") + "\"}";
					response.getOutputStream().write(s.getBytes("utf-8"));
					response.setContentType("application/json;charset=UTF-8");
					HttpServletResponse res = (HttpServletResponse)response;
					res.setStatus(200);
				}else {
					
					String s = "{\"code\":\"300000\",\"message\":\"" + e.getMessage() + "\"}";
					response.getOutputStream().write(s.getBytes("utf-8"));
					
					HttpServletResponse res = (HttpServletResponse)response;
					res.setStatus(200);
					response.setContentType("application/json;charset=UTF-8");
				}
			} else if (e2 instanceof TipException) {
				TipException e = (TipException)e2;
				String s = "{\"code\":\"" + e.getCode() + "\",\"message\":\"" + e.getLocalizedMessage() + "\"}";
				response.getOutputStream().write(s.getBytes("utf-8"));
				response.setContentType("application/json;charset=UTF-8");
				HttpServletResponse res = (HttpServletResponse)response;
				res.setStatus(200);
			}  else if(e2 instanceof org.springframework.dao.DuplicateKeyException){
				SQLIntegrityConstraintViolationException e3 = (SQLIntegrityConstraintViolationException)e2.getCause();
				
				String[] ss = e3.getMessage().split("'");
				
				String s = "{\"code\":\"200000\",\"message\":\""+URLEncoder.encode(ss[ss.length - 1]+"重复", "utf8")+"\"}";
				response.getOutputStream().write(s.getBytes("utf-8"));
				response.setContentType("application/json;charset=UTF-8");
				HttpServletResponse res = (HttpServletResponse)response;
				res.setStatus(200);
			}   else {
				LOG.error(ThreadLocalUtils.getTraceId(), e1);
				
				String s = "{\"code\":\"300000\",\"message\":\"" + e1.getMessage() + "\"}";
				response.getOutputStream().write(s.getBytes("utf-8"));
				
				HttpServletResponse res = (HttpServletResponse)response;
				res.setStatus(200);
				response.setContentType("application/json;charset=UTF-8");
			}
		} catch (Exception e) {
			LOG.error(ThreadLocalUtils.getTraceId(), e);
			
			String s = "{\"code\":\"300000\",\"message\":\"" + e.getMessage() + "\"}";
			response.getOutputStream().write(s.getBytes("utf-8"));
			
			HttpServletResponse res = (HttpServletResponse)response;
			res.setStatus(200);
			response.setContentType("application/json;charset=UTF-8");
		}
	}

	public void destroy() {
	}
}