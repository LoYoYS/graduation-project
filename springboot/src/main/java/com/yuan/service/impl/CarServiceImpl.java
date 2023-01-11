package com.yuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.domain.Car;
import com.yuan.domain.ResultData;
import com.yuan.mapper.CarMapper;
import com.yuan.mapper.CoachMapper;
import com.yuan.qo.CarQo;
import com.yuan.qo.UpdateCarQo;
import com.yuan.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CoachMapper coachMapper;

    @Override
    public List<String> numberList() {
        return carMapper.numberList();
    }

    @Override
    public PageInfo<Car> list(CarQo qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getSize());
        List<Car> list = carMapper.list(qo);
        return new PageInfo<>(list);
    }

    @Override
    public ResultData<String> saveCar(Car car) {
        Car carByNumber = carMapper.findCarByNumber(car.getNumber());
        if(carByNumber!=null)
            return ResultData.fail("添加失败，车辆档案已存在!");
        Integer save = carMapper.save(car);
        if(0<save){
            coachMapper.updateCar(car.getNumber(),car.getCoach_id());
            return ResultData.success("添加成功!");
        }
        else
            return ResultData.fail("添加失败");
    }

    @Override
    public ResultData<String> update(UpdateCarQo qo) {
        Integer integer = carMapper.updateCar(qo.getNewCar());
        if(0<integer){
            coachMapper.updateCar(qo.getNewCar().getNumber(),qo.getNewCar().getCoach_id());
            coachMapper.updateCar(null, qo.getOldCar().getCoach_id());
            return ResultData.success("修改成功！");
        }
        return ResultData.fail("修改失败！");
    }

    @Override
    public ResultData<String> delete(Car car) {
        Integer delete = carMapper.delete(car.getId());
        if(0<delete){
            coachMapper.updateCar(null,car.getCoach_id());
            return ResultData.success("删除成功！");
        }
        else
            return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<String> deleteList(List<Car> list) {
        Integer integer = carMapper.deleteList(list);
        if(0<integer){
            coachMapper.updateList(list);
            return ResultData.success("删除成功！");
        }
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<Car> getCarInfo(String number) {
        Car carByNumber = carMapper.findCarByNumber(number);
        return ResultData.success(carByNumber);
    }

}
