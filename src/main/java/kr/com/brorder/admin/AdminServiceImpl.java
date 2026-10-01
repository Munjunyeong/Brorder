package kr.com.brorder.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.brorder.users.Users;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminDao dao;

	@Override
	public List<Users> userlist(Pager pager) {
		int total = dao.usertotal(pager);
		
		pager.setTotal(total);
		
		return dao.userlist(pager);
	}

}
