package com.yuan.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachQo {
    private String keyWord;
    private String sex;
    private Integer size;
    private Integer currentPage;
}
