package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Integer id;
    private String brand;
    private String url;
    private Integer type;
    private String number;
    private Integer coach_id;
    private String coach_name;
}
