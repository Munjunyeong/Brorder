package kr.com.brorder.users;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		Users users = (Users) session.getAttribute("users");
		
		if(users != null) {
			String role = users.getRole().toUpperCase();
		
			if(role.equals("USER")) {
				return true;
			}
		}

		response.sendRedirect("/");
		return false;
	}

}
