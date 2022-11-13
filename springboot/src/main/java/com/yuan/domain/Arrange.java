package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arrange {
    private Integer id;
    private Integer c_id;
    private Integer work;
    private Integer subject;
    private String type;
    private Date date;
    private Integer a=4;
    private Integer b=4;
    private Integer c=4;
    private Integer d=4;
    private Integer e=4;
    private Integer f=4;
}
