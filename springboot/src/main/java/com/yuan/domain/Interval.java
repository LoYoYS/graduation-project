package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interval {
    private Integer id;
    private Integer a_id;
    private Integer c_id;
    private String start;
    private String end;
    private Integer number;
}
