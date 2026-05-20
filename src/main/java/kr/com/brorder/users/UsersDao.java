package kr.com.brorder.users;

public interface UsersDao {

	void register(Users item);

	Users item(String id);

	Users findid(Users item);

	Users findpw(Users item);

}
