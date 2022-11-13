package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer o_id;
    private Integer s_id;
    private Integer c_id;
    private Integer subject;
    private String date;
    private String o_date;
    private String time;
    private String content;
    private String c_name;
    private String s_name;
    private Integer start;
    private Integer hidden;
}