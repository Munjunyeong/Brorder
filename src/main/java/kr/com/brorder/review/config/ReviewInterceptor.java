package kr.com.brorder.review.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.com.brorder.users.Users;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@Component
public class ReviewInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        // 로그인 세션 가방이 비어있거나 아이디가 null인 비회원 상태일 때
        if (loginUser == null || loginUser.getUserid() == null) {
            System.out.println("[인터셉터 통제] 비로그인 사용자 감지 -> 알림창 표시 후 로그인 페이지로 강제 이동");

            // 1. 브라우저가 다른 작업을 하기 전에 응답 버퍼를 완전히 비우고 인코딩 정렬
            response.reset();
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            // 2. 브라우저 화면에 직접 자바스크립트 코드를 확실하게 밀어 넣음
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('로그인 후 이용해 주세요.');"); // 종민님이 원하시는 팝업 문구
            out.println("location.href='" + request.getContextPath() + "/login';"); // 확인 누르면 로그인 창으로 이동
            out.println("</script>");

            // 3. 버퍼에 남아있는 찌꺼기까지 밀어내고 스트림 닫기
            out.flush();
            out.close();

            return false; // 컨트롤러 진입을 철저하게 차단
        }

        return true; // 로그인 성공 상태이므로 무사 통과 시킴
    }
}