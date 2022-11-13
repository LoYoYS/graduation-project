package com.yuan.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarQo {
    private String keyWord;
    private Integer type;
    private Integer size;
    private Integer currentPage;
}
