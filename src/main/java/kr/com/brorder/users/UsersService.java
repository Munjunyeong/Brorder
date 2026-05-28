package kr.com.brorder.users;

import java.util.List;

public interface UsersService {

	void register(Users item);

	boolean login(Users item);

	Users findid(Users item);

	Users findpw(Users item);

	void update(Users item);

	List<Address> list(Long userid);

}
