package com.yuan.mapper;

import com.yuan.domain.Car;
import com.yuan.domain.Coach;
import com.yuan.qo.CarQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper {
    List<String> numberList();

    List<Car> list(CarQo qo);

    Integer update(Integer id, String number);

    void updateList(List<Coach> list);

    Car findCarByNumber(String number);

    Integer save(Car car);

    Integer updateCar(Car car);

    Integer delete(Integer id);

    Integer deleteList(List<Car> list);

}