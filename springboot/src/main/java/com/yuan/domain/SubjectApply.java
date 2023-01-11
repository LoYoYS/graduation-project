package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectApply {
    private Integer id;
    private Integer s_id;
    private String name;
    private String phone;
    private String date;
    private String subject;
    private String url;
    private String[] urls;
    private Integer status=0;
}
