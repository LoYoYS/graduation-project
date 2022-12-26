package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coach {
    private Integer id;
    private String name;
    private String sex;
    private String id_number;
    private String phone;
    private String date;
    private String car;
    private Integer isBindWx=0;

}
