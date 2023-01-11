package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String longitude;
    private String latitude;
    private String logo;
    private String back;
    private String slogan;
    private String[] slogans;
    private String text;

}
