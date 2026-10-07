package kr.com.brorder.wish;

import jakarta.servlet.http.HttpSession;
import kr.com.brorder.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wish")
public class WishRestController {

    @Autowired
    private WishService wishService;

    @PostMapping("/toggle/{storeId}")
    public ResponseEntity<Map<String, Object>> toggleWish(@PathVariable("storeId") Integer storeId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Users users = (Users) session.getAttribute("users");

        if (users == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(response);
        }

        try {
            boolean isWished = wishService.toggleWish(users.getUserid(), storeId);
            response.put("success", true);
            response.put("isWished", isWished);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "처리 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }
}
