package kr.com.brorder.admin;

import java.util.List;

import kr.com.brorder.users.Users;

public interface AdminDao {

	List<Users> userlist(Pager pager);

	int usertotal(Pager pager);

}
