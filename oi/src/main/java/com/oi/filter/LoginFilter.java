package com.oi.filter;

import java.io.IOException;

import com.oi.dto.LoginDTO;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// request 필터

		// 로그인 체크
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		HttpSession session = req.getSession();
		
		LoginDTO info = (LoginDTO)session.getAttribute("member");
		
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		if(info == null && isExcludeUrl(req) == false ) {
			if(req.getHeader("AJAX") != null && req.getHeader("AJAX").equals("true")) {
				resp.sendError(403);
			}else {
				// uri 에서 cp 제거
				if(uri.indexOf(req.getContextPath()) == 0) {
					uri = uri.substring(req.getContextPath().length());
				}
				uri = "redirect:" + uri;
				
				String queryString = req.getQueryString();
				if(queryString != null) {
					uri += "?" + queryString;
				}
				// 로그인 전 주소를 세션에 저장
				session.setAttribute("preLoginURI", uri);
				
				resp.sendRedirect(cp + "/access/login");
			}
			return;
		}// 관리자메뉴를 일반인이 접근한경우 를 처리하지않음 아직 
		
		
		chain.doFilter(request, response);
	}
	private boolean isExcludeUrl(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		uri = uri.substring(cp.length());
		
		// 로그인 체크를 하지 않아도 되는 URL
		String[] uris = {
			// 추후에 본인들이 추가하기 
			"/main", "/access/login",
			"/index.jsp","/access/logout",
			"/uploads/photo/**",
			"/resources/**",
			"/QnA/main",
			"/findGymController/**"
		};
		
		if(uri.length() <= 1) {
			return true;
		}
		
		for(String s: uris) {
			if(s.lastIndexOf("**") != -1) {
				s = s.substring(0, s.lastIndexOf("**"));
				if(uri.indexOf(s) == 0) {
					return true;
				}
			} else if(uri.equals(s)) {
				return true;
			}
		}
		
		return false;
	}
}
