package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private String sex;
    private String id_number;
    private String phone;
    private Date date;
    private String type;
    private Subject subject;
    private Integer isBindWx=0;
}
