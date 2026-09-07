package kr.com.brorder.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.com.brorder.users.Users;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	AdminService service;
	
	@GetMapping("/userlist")
	String userlist(Model model, Pager pager) {
		List<Users> userlist = service.userlist(pager);
		
		model.addAttribute("userlist", userlist);
		
		return "admin/userlist";
	}
	
	@GetMapping("/storelist")
	String storelist(Model model, Pager pager) {
		
		
		return "admin/storelist";
	}

}
