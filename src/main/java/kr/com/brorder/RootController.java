package kr.com.brorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import kr.com.brorder.users.Users;
import kr.com.brorder.users.UsersService;

@Controller
public class RootController {
	
	@Autowired
	UsersService usersService;
	
	@GetMapping("/")
	String index() {
		return "index"; //메인페이지
	}
	
	@GetMapping("/register")
	String register() {
		return "register"; //회원가입 페이지
	}
	
	@PostMapping("/register")
	String register(Users item) {
		usersService.register(item); //프론트에서 받은 데이터를 Users.java를 item이라는 이름으로 만들어서 데이터를 담고 서비스에게 넘김
		
		return "redirect:"; // redirect : 해당 주소로 보내는 역할
	}
	
	@GetMapping("/login")
	String login() {
		return "login"; //로그인 페이지
	}
	
	@PostMapping("/login")
	String login(Users item, HttpSession session) {
		if(usersService.login(item)) {
			session.setAttribute("users", item); //서비스에서 받아온 item을 세션에 users라는 이름으로 넣어둠
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}

	@GetMapping("/findid")
	String findid() {
		return "user/findid";
	}
	
	@PostMapping("/findid")
	String findid(Users item, Model model) {
		Users findid = usersService.findid(item);
		
	    model.addAttribute("findid", findid);
		
		return "user/resultid";
	}
	
	@GetMapping("/findpw")
	String findpw() {
		return "user/findpw";
	}
	
	@PostMapping("/findpw")
	String findpw(Users item, Model model) {
		Users findpw = usersService.findpw(item);
		
		model.addAttribute("findpw", findpw);
		
		return "user/resultpw";
	}

}
