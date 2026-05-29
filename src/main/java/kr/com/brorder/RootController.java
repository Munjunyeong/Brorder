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
		return "index";
	}
	
	@GetMapping("/register")
	String register() {
		return "register";
	}
	
	@PostMapping("/register")
	String register(Users item) {
		usersService.register(item);
		
		return "redirect:";
	}
	
	@GetMapping("/login")
	String login() {
		return "login";
	}
	
	@PostMapping("/login")
	String login(Users item, HttpSession session) {
		if(usersService.login(item)) {
			session.setAttribute("users", item);
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
