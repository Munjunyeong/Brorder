package kr.com.brorder.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> getMenusByStoreId(Integer storeId) {
        return menuDao.selectMenuListByStoreId(storeId);
    }

    @Override
    public Menu getMenuById(Integer menuId) {
        return menuDao.selectMenuById(menuId);
    }

    @Override
    public void addMenu(Menu menu) {
        menuDao.insertMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDao.updateMenu(menu);
    }

    @Override
    public void removeMenu(Integer menuId) {
        menuDao.deleteMenu(menuId);
    }
}
