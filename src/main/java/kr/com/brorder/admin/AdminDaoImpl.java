package kr.com.brorder.admin;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.brorder.users.Users;

@Repository
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	SqlSession sql;

	@Override
	public List<Users> userlist(Pager pager) {
		
		return sql.selectList("admin.userlist", pager);
	}

	@Override
	public int usertotal(Pager pager) {
		return sql.selectOne("admin.usertotal", pager);
	}

}
