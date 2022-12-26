package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arrange {
    private Integer id;
    private Integer c_id;
    private Integer work;
    private Integer subject;
    private String type;
    private String date;
    List<Interval> list;
}
