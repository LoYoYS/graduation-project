package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacate {
    private Integer id;
    private Integer c_id;
    private String name;
    private String content;
    private String phone;
    private Integer type;
    private String startTime;
    private String endTime;
    private Integer status;
    private String date;
}
