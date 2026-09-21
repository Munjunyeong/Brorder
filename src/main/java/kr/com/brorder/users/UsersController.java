package kr.com.brorder.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	
	@Autowired
	UsersService service;

	@GetMapping("/users/my")
	String detail(Model model, HttpSession session) {
	    Users users = (Users) session.getAttribute("users");
		
		model.addAttribute("users", users);
		
		return "user/mypage";
	}
	
	@GetMapping("/users/update")
	String update(Model model, HttpSession session) {
	    Users users = (Users) session.getAttribute("users");
		
		model.addAttribute("users", users);
		
		return "user/update";
	}
	
	@PostMapping("/users/update")
	String update(Users item, HttpSession session) {
		Users users = (Users) session.getAttribute("users");
		
		item.setId(users.getId());
		
		service.update(item);
		
		session.setAttribute("users", item);
		
		return "redirect:/users/my";
	}
	
	@GetMapping("/users/my/address")
	String address(Model model, HttpSession session) {
		Users users = (Users) session.getAttribute("users");
		
		List<Address> list = service.list(users.getUserid());
		
		model.addAttribute("list", list);
		
		return "user/address";
	}
	
	@GetMapping("/users/my/address/add")
	String addaddress() {
		return "user/addaddress";
	}
	
	@PostMapping("/users/my/address/add")
	String addaddress(Address item, HttpSession session) {
		Users users = (Users) session.getAttribute("users");
		item.setUserid(users.getUserid());
		
		service.addaddress(item);
		
		return "redirect:/users/my/address";
	}
	
	@GetMapping("/users/my/address/delete/{addressid}")
	String deleteaddress(@PathVariable Long addressid) {
		service.deleteaddress(addressid);
		
		return "redirect:/users/my/address";
	}
	
	@GetMapping("/users/my/address/update/{addressid}")
	String updateAddress(@PathVariable Long addressid, Model model) {
		Address item = service.addressitem(addressid);
		
		model.addAttribute("item", item);
		
		return "user/addressupdate";
	}
	
	@PostMapping("/users/my/address/update/{addressid}")
	String updateAddress(@PathVariable Long addressid, Address item) {
		service.updateaddress(item);
		return "redirect:/users/my/address";
	}
	
	@GetMapping("/users/my/order")
	String myorder(Model model, HttpSession session) {
		
		
		return "user/myorder";
	}

}
