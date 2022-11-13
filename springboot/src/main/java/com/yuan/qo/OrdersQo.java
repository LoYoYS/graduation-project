package com.yuan.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersQo {
    private Integer id;
    private Integer s_id;
    private Integer c_id;
    private Integer status;
    private String date;
    private String interval;
}
