package com.yuan.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacateQo {
    private String keyWord;
    private Integer status;
    private Integer size;
    private Integer currentPage;
}
