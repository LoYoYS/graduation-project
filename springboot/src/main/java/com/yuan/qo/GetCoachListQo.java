package com.yuan.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
* 此类为查询预约练车里的教练列表
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCoachListQo {
    private String date;
    private String type;
    private Integer subject;
}
