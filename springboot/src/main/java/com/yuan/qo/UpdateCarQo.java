package com.yuan.qo;


import com.yuan.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarQo {
    private Car oldCar;
    private Car newCar;
}
