package kr.com.brorder.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@GetMapping("/userlist")
	String userlist(Model model) {
		return "admin/userlist";
	}

}
