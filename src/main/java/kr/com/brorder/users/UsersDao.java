package kr.com.brorder.users;

import java.util.List;

public interface UsersDao {

	void register(Users item);

	Users item(String id);

	Users findid(Users item);

	Users findpw(Users item);

	void update(Users item);

	List<Address> list(Long userid);

}
