package com.yuan.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQo {
    private String keyWord;
    private String type;
    private String subject;
    private Integer size;
    private Integer currentPage;
}
