package com.happyweekend.service;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ImageDao;
import com.happyweekend.models.Image;
import com.happyweekend.service.interfaces.IImageService;

import java.sql.Connection;
import java.sql.SQLException;

public class ImageService implements IImageService {

    private static final int DEFAUTL_PROFILE_IMAGE_ID = 1;

    @Override
    public void save(Image image) {
        Connection con = ConnectionManager.getInstance().connect();
        try{
            ImageDao dao = new ImageDao(con);
            if(image.getId()==null){
                dao.save(image);
            }else{
                dao.update(image);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image get(int id) {
        Connection con = ConnectionManager.getInstance().connect();
        try{
            ImageDao dao = new ImageDao(con);
            Image img = dao.get(id);
            con.close();
            return img;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Image getDefaultProfileImage() {
        return get(DEFAUTL_PROFILE_IMAGE_ID);
    }
}
