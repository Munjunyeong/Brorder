package kr.com.brorder.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/update")
	String update(Model model, HttpSession session) {
	    Users users = (Users) session.getAttribute("users");
		
		model.addAttribute("users", users);
		
		return "user/update";
	}
	
	@PostMapping("/update")
	String update(Users item, HttpSession session) {
		Users users = (Users) session.getAttribute("users");
		
		item.setId(users.getId());
		
		service.update(item);
		
		session.setAttribute("users", item);
		
		return "redirect:/users/my";
	}
	
	@GetMapping("/my/address")
	String address(Model model, HttpSession session) {
		Users users = (Users) session.getAttribute("users");
		
		List<Address> list = service.list(users.getUserid());
		
		model.addAttribute("list", list);
		
		return "user/address";
	}

}
