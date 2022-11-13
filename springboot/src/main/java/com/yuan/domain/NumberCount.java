package com.yuan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberCount {
    private Integer studentCount;
    private Integer coachCount;
    private Integer userCount;
    private Integer carCount;
}
