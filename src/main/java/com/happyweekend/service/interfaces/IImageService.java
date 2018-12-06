package com.happyweekend.service.interfaces;

import com.happyweekend.models.Image;

public interface IImageService{

    void save(Image image);
    Image get(int id);
}
