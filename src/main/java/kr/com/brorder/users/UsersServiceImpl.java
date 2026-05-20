package kr.com.brorder.users;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	UsersDao usersDao;

	@Override
	public void register(Users item) {
		usersDao.register(item);
	}

	@Override
	public boolean login(Users item) {
		Users users = usersDao.item(item.getId());
		if(users != null && users.getPassword().equals(item.getPassword())) {
			BeanUtils.copyProperties(users, item);
			item.setPassword(null);
			return true;
		}
		
		return false;
	}

	@Override
	public Users findid(Users item) {
		return usersDao.findid(item);
	}

	@Override
	public Users findpw(Users item) {
		return usersDao.findpw(item);
	}

}
