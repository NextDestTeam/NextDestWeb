package com.happyweekend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Getter
    @Setter
    private Integer id;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private byte[] image;
}
