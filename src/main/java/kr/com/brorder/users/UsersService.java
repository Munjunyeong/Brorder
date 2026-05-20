package kr.com.brorder.users;

public interface UsersService {

	void register(Users item);

	boolean login(Users item);

	Users findid(Users item);

	Users findpw(Users item);

}
