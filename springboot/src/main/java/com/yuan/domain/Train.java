package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train {
    private Integer id;
    private String name;
    private String sex;
    private String id_number;
    private String phone;
    private String status;
    private Car car;
}
