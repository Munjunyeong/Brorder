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
            System.out.println("[인터셉터 통제] 비로그인 사용자 감지 -> 모달 표시 후 로그인 페이지로 강제 이동");

            // 1. 브라우저가 다른 작업을 하기 전에 응답 버퍼를 완전히 비우고 인코딩 정렬
            response.reset();
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            String ctx = request.getContextPath();

            // 2. Uiverse 쿠키카드 스타일을 그대로 가져와 로그인 안내 모달로 변형해서 직접 그림
            //    (이 응답은 Thymeleaf를 안 타므로 head.html의 폰트어썸/CSS가 없음 -> 전부 인라인으로 넣음)
// 2. Uiverse 쿠키카드 스타일을 블루/화이트/그레이 테마 및 슬라이드 다운 애니메이션으로 변경
// 2. 부드럽고 천천히 내려오는 슬라이드 애니메이션과 깊이감 있는 딤드/블러 배경 적용
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang='ko'><head><meta charset='UTF-8'>");
            out.println("<style>");
            out.println("html,body{margin:0;height:100%;font-family:'Noto Sans KR',-apple-system,sans-serif;}");

            // 뒷배경: 메인화면을 유지하면서 더 어둡고 깊이감 있는 회색 톤 + 확실한 블러 처리
            out.println(".login-modal-overlay{position:fixed;inset:0;background:rgba(20,20,20,.75);backdrop-filter:blur(6px);-webkit-backdrop-filter:blur(6px);display:flex;align-items:center;justify-content:center;z-index:99999;}");

            // 카드 스타일 & 천천히 우아하게 내려오는 애니메이션 (duration을 0.6초로 늘림)
            out.println(".card{width:310px;height:230px;background-color:#fff;border-radius:18px;display:flex;flex-direction:column;align-items:center;justify-content:center;padding:24px 30px;gap:14px;position:relative;overflow:hidden;box-shadow:0 20px 45px rgba(0,0,0,.3);animation: slideDownSlow .6s cubic-bezier(.16, 1, .3, 1) both;}");
            out.println("@keyframes slideDownSlow{from{opacity:0;transform:translateY(-70px);}to{opacity:1;transform:translateY(0);}}");

            // 블루 계열 아이콘 박스
            out.println(".login-modal-icon{width:48px;height:48px;border-radius:50%;background:rgba(59,130,246,.1);display:flex;align-items:center;justify-content:center;}");
            out.println(".login-modal-icon svg{width:24px;height:24px;fill:#3b82f6;}");

            out.println(".cookieHeading{font-size:1.15em;font-weight:800;color:#1a1a1a;margin:0;}");
            out.println(".cookieDescription{text-align:center;font-size:.85em;font-weight:500;color:#6b7280;line-height:1.55;margin:0;}");
            out.println(".buttonContainer{display:flex;gap:12px;flex-direction:row;margin-top:4px;}");

            // 메인 블루 버튼 (로그인 하러가기)
            out.println(".acceptButton{width:125px;height:38px;background-color:#3b82f6;border:none;color:#fff;cursor:pointer;font-weight:700;font-size:13px;border-radius:20px;box-shadow:0 4px 12px rgba(59,130,246,.35);transition:all .2s ease;}");
            out.println(".acceptButton:hover{background-color:#2563eb;box-shadow:0 6px 16px rgba(59,130,246,.45);transform:translateY(-1px);}");

            // 그레이 취소 버튼 (홈으로)
            out.println(".declineButton{width:85px;height:38px;background-color:#f3f4f6;color:#4b5563;border:1px solid #e5e7eb;cursor:pointer;font-weight:700;font-size:13px;border-radius:20px;transition:all .2s ease;}");
            out.println(".declineButton:hover{background-color:#e5e7eb;color:#1f2937;}");
            out.println("</style></head><body>");

            out.println("<div class='login-modal-overlay'>");
            out.println("  <div class='card'>");
            out.println("    <div class='login-modal-icon'>");
            out.println("      <svg viewBox='0 0 24 24'><path d='M6 10V8a6 6 0 1 1 12 0v2h1a1 1 0 0 1 1 1v9a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2v-9a1 1 0 0 1 1-1h1zm2 0h8V8a4 4 0 1 0-8 0v2zm4 4a1.5 1.5 0 0 0-1 2.62V18h2v-1.38A1.5 1.5 0 0 0 12 14z'/></svg>");
            out.println("    </div>");
            out.println("    <p class='cookieHeading'>로그인이 필요해요</p>");
            out.println("    <p class='cookieDescription'>서비스를 이용하시려면<br>로그인 후 다시 이용해 주세요.</p>");
            out.println("    <div class='buttonContainer'>");
            out.println("      <button class='acceptButton' onclick=\"location.href='" + ctx + "/login'\">로그인 하러가기</button>");
            out.println("      <button class='declineButton' onclick=\"location.href='" + ctx + "/'\">홈으로</button>");
            out.println("    </div>");
            out.println("  </div>");
            out.println("</div>");

            out.println("</body></html>");

            // 3. 버퍼에 남아있는 찌꺼기까지 밀어내고 스트림 닫기
            out.flush();
            out.close();

            return false; // 컨트롤러 진입을 철저하게 차단
        }

        return true; // 로그인 성공 상태이므로 무사 통과 시킴
    }
}