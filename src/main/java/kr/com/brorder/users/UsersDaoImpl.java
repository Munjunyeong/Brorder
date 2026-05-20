package kr.com.brorder.users;

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

}
