package kr.com.brorder.store;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.com.brorder.users.Users;
import org.springframework.web.servlet.HandlerInterceptor;

public class StoreInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        Users users = (Users) session.getAttribute("users");

        //  미로그인 상태 원천 차단 (500 에러 해결)
        if (users == null) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('로그인이 필요한 서비스입니다.'); location.href='/';</script>");
            return false;
        }

        // 일반 고객(USER) 또는 권한 값 자체가 없는 유저 차단 (유저 접속 허용 에러 해결)
        if (users.getRole() == null || "USER".equalsIgnoreCase(users.getRole().trim())) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('가게 관리 권한(사장님/관리자)이 없습니다.'); location.href='/';</script>");
            return false;
        }

        // 사장님(OWNER) 또는 관리자(ADMIN)만 통과
        String role = users.getRole().toUpperCase().trim();
        if ("OWNER".equals(role) || "ADMIN".equals(role)) {
            return true;
        }

        response.sendRedirect("/");
        return false;
    }
}