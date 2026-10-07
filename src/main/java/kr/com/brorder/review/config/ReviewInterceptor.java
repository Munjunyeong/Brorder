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
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("users");

        // 로그인하지 않은 경우
        if (loginUser == null || loginUser.getUserid() == null) {
            System.out.println("[인터셉터] 비로그인 사용자 감지");

            response.reset();
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            String ctx = request.getContextPath();
            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html lang='ko'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<style>");

            out.println("html,body{margin:0;height:100%;font-family:'Noto Sans KR',-apple-system,sans-serif;}");
            out.println(".login-modal-overlay{position:fixed;inset:0;background:rgba(20,20,20,.75);backdrop-filter:blur(6px);-webkit-backdrop-filter:blur(6px);display:flex;align-items:center;justify-content:center;z-index:99999;}");
            out.println(".card{width:310px;height:230px;background:#fff;border-radius:18px;display:flex;flex-direction:column;align-items:center;justify-content:center;padding:24px 30px;gap:14px;position:relative;overflow:hidden;box-shadow:0 20px 45px rgba(0,0,0,.3);animation:slideDownSlow .6s cubic-bezier(.16,1,.3,1) both;}");
            out.println("@keyframes slideDownSlow{from{opacity:0;transform:translateY(-70px)}to{opacity:1;transform:translateY(0)}}");
            out.println(".login-modal-icon{width:48px;height:48px;border-radius:50%;background:rgba(59,130,246,.1);display:flex;align-items:center;justify-content:center;}");
            out.println(".login-modal-icon svg{width:24px;height:24px;fill:#3b82f6;}");
            out.println(".cookieHeading{font-size:1.15em;font-weight:800;color:#1a1a1a;margin:0;}");
            out.println(".cookieDescription{text-align:center;font-size:.85em;font-weight:500;color:#6b7280;line-height:1.55;margin:0;}");
            out.println(".buttonContainer{display:flex;gap:12px;flex-direction:row;margin-top:4px;}");
            out.println(".acceptButton{width:125px;height:38px;background:#3b82f6;border:none;color:#fff;cursor:pointer;font-weight:700;font-size:13px;border-radius:20px;box-shadow:0 4px 12px rgba(59,130,246,.35);transition:all .2s ease;}");
            out.println(".acceptButton:hover{background:#2563eb;box-shadow:0 6px 16px rgba(59,130,246,.45);transform:translateY(-1px);}");
            out.println(".declineButton{width:85px;height:38px;background:#f3f4f6;color:#4b5563;border:1px solid #e5e7eb;cursor:pointer;font-weight:700;font-size:13px;border-radius:20px;transition:all .2s ease;}");
            out.println(".declineButton:hover{background:#e5e7eb;color:#1f2937;}");

            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

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

            out.println("</body>");
            out.println("</html>");

            out.flush();
            out.close();

            return false;
        }

        return true;
    }
}