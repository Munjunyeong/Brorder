package kr.com.brorder.users;

import java.util.List;

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

	@Override
	public void update(Users item) {
		usersDao.update(item);
	}

	@Override
	public List<Address> list(Long userid) {
		return usersDao.list(userid);
	}
	@Override
	public void addaddress(Address item) {
		usersDao.addaddress(item);
	}

	@Override
	public void deleteaddress(Long addressid) {
		usersDao.deleteaddress(addressid);
	}

	@Override
	public Address addressitem(Long addressid) {
		return usersDao.addressitem(addressid);
	}

	@Override
	public void updateaddress(Address item) {
		usersDao.updateaddress(item);
	}

	@Override
	public Users item(String id) {
		return usersDao.item(id);
	}

}
