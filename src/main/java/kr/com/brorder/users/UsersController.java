package kr.com.brorder.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	UsersService service;

	@GetMapping("/my")
	String detail(Model model, HttpSession session) {
	    Users users = (Users) session.getAttribute("users");
		
		model.addAttribute("users", users);
		
		return "user/detail";
	}

}
