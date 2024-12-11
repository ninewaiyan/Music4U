package com.music4u.controllers;

import java.io.IOException;
import java.net.http.HttpRequest;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		HttpSession session = httpRequest.getSession();
		boolean isAuth = session.getAttribute("user") !=null;
		boolean isLogin = httpRequest.getRequestURI().contains("login");
		boolean isRegister = httpRequest.getRequestURI().contains("user");
		
		
		if(isAuth || isLogin || isRegister) {
			filterChain.doFilter(httpRequest, httpResponse);
		}else {
			httpResponse.sendRedirect("login");
		}
		 
		
	}

}
