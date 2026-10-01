package kr.com.brorder.admin;

import java.util.List;

import kr.com.brorder.users.Users;

public interface AdminService {

	List<Users> userlist(Pager pager);

}
