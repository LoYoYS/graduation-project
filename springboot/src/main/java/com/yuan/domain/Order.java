package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Integer s_id;
    private Integer c_id;
    private Integer subject;
    private String date;
    private String time;
    private String startTime;
    private String endTime;
    private String interval;
    private Integer status=0;
    private Integer comment=0;
    private Integer isDelete=0;
    private String s_name;
    private String s_phone;
    private String c_name;
    private String c_phone;
    private String carNumber;
}