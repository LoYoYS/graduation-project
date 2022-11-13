package com.yuan.service;

import com.github.pagehelper.PageInfo;
import com.yuan.domain.Car;
import com.yuan.domain.ResultData;
import com.yuan.qo.CarQo;
import com.yuan.qo.UpdateCarQo;

import java.util.List;

public interface CarService {
    //获取使用者为空的车辆
    List<String> numberList();
    PageInfo<Car> list(CarQo qo);
    ResultData<String> saveCar(Car car);
    ResultData<String> update(UpdateCarQo qo);
    ResultData<String> delete(Car car);
    ResultData<String> deleteList(List<Car> list);
    //获取车辆信息
    ResultData<Car> getCarInfo(String number);
}
