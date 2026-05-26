package kr.com.brorder.review.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * [리뷰 권한 검증 인터셉터]
 * 리뷰 관련 요청이 컨트롤러에 도달하기 전, 사용자의 로그인 세션 상태를 가로채서 검증하는 방어벽 클래스임
 */
@Component
public class ReviewAuthInterceptor implements HandlerInterceptor {

    /**
     * [컨트롤러 실행 전 사전 가로채기]
     * 세션 금고를 열어 'loginUserId'가 없으면 요청을 중단하고 로그인 페이지(/login)로 강제 이동시킴
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Integer loginUserId = (Integer) session.getAttribute("loginUserId");

        // 로그인하지 않은 비회원인 경우의 처리 구역임
        if (loginUserId == null) {
            // 조원들과 합의한 로그인 페이지 경로(/login)로 리다이렉트 처리함
            response.sendRedirect("/login");
            return false; // 컨트롤러로 요청을 더 이상 진행시키지 않고 여기서 차단함
        }

        return true; // 로그인 세션이 확인되면 컨트롤러의 해당 메서드로 진입을 허용함
    }
}