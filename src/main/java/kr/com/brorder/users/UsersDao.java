package kr.com.brorder.users;

import java.util.List;

public interface UsersDao {

	void register(Users item);

	Users item(String id);

	Users findid(Users item);

	Users findpw(Users item);

	void update(Users item);

	List<Address> list(Long userid);

	void addaddress(Address item);

	void deleteaddress(Long addressid);

	Address addressitem(Long addressid);

	void updateaddress(Address item);

}
