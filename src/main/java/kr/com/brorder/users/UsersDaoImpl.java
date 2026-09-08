package kr.com.brorder.users;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDaoImpl implements UsersDao {
	
	@Autowired
	SqlSession sql;

	@Override
	public void register(Users item) {
		sql.insert("users.register", item);
	}

	@Override
	public Users item(String id) {
		return sql.selectOne("users.item", id);
	}

	@Override
	public Users findid(Users item) {
		return sql.selectOne("users.findid", item);
	}

	@Override
	public Users findpw(Users item) {
		return sql.selectOne("users.findpw", item);
	}

	@Override
	public void update(Users item) {
		sql.update("users.update", item);
	}

	@Override
	public List<Address> list(Long userid) {
		return sql.selectList("users.address", userid);
	}

	@Override
	public void addaddress(Address item) {
		sql.insert("users.addaddress", item);
	}

	@Override
	public void deleteaddress(Long addressid) {
		sql.delete("users.deleteaddress", addressid);
	}

	@Override
	public Address addressitem(Long addressid) {
		return sql.selectOne("users.addressitem", addressid);
	}

	@Override
	public void updateaddress(Address item) {
		sql.update("users.updateaddress", item);
	}


}
