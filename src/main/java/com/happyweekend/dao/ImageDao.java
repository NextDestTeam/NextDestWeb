package com.happyweekend.dao;


import com.happyweekend.models.Image;

import javax.servlet.SessionTrackingMode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao implements Dao<Image> {

    private Connection connection;

    public ImageDao(Connection connection){
        this.connection = connection;
    }


    @Override
    public Image get(Integer id) throws SQLException {
        String sql = "SELECT id, name, image "+
                "FROM public.image WHERE ID = ?";
        Image image;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs!=null) {
                rs.next();
                return generateImage(rs);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Image> getAll() {
        String sql = "SELECT id, name, image "+
        "FROM public.image";
        List<Image> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs!=null) {
                while (rs.next()){
                    result.add(generateImage(rs));
                }
            }
            return result;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Image generateImage(ResultSet rs) throws SQLException {
        Image img = new Image();
        img.setId(rs.getInt(1));
        img.setName(rs.getString(2));
        img.setImage(rs.getBytes(3));
        return img;
    }

    @Override
    public void save(Image image) {
        String sql = "INSERT INTO image(name,image) VALUES (?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,image.getName());
            InputStream is = new ByteArrayInputStream(image.getImage());
            stmt.setBinaryStream(2,is);

            stmt.executeUpdate();
            if(stmt.getGeneratedKeys().next()) {
                image.setId(stmt.getGeneratedKeys().getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Image image) {
        String sql = "UPDATE public.image SET id=?, name=?, image=? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,image.getId());
            stmt.setString(2,image.getName());
            stmt.setBytes(3,image.getImage());
            stmt.setInt(4,image.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Image image) {

    }
}
