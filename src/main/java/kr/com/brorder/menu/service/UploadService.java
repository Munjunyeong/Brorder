package kr.com.brorder.menu.service;

import kr.com.brorder.menu.dao.MenuDao;
import kr.com.brorder.menu.model.Menu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    private final MenuDao menuDao;

    public UploadService(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Value("${kopo.upload.path}")
    private String uploadPath;

    public String uploadFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return null;
        }

        String originalName = file.getOriginalFilename();
        String saveName = System.currentTimeMillis() + "_" + originalName;

        File saveFile = new File(uploadPath, saveName);
        file.transferTo(saveFile);

        return saveName;
    }

    public void insertMenu(Menu menu, MultipartFile file) throws IOException {

        String imageName = uploadFile(file);

        if (imageName != null) {

            menu.setImage(imageName);
        }

        menuDao.insertMenu(menu);
    }
}