package kr.com.brorder.menu.service;

import kr.com.brorder.menu.dao.MenuDao;
import kr.com.brorder.menu.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;

    public MenuServiceImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public List<Menu> selectMenuListByStoreId(Integer storeId) {

        return menuDao.selectMenuListByStoreId(storeId);
    }
}