package com.yuan.controller;

import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.Car;
import com.yuan.domain.ResultData;
import com.yuan.qo.CarQo;
import com.yuan.qo.UpdateCarQo;
import com.yuan.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Value("${Location.realPath}")
    private String realPath;
    @Value("${Location.mapperPath}")
    private String mapperPath;

    @RequestMapping("/getList")
//    @isCheckToken
    public ResultData<PageInfo<Car>> getList(CarQo qo){ return ResultData.success(carService.list(qo));
    }

    @RequestMapping("/list")
//    @isCheckToken
    public ResultData<List<String>> list(){
        return ResultData.success(carService.numberList());
    }

    @RequestMapping("/getUrl")
    public ResultData<String> getUrl(MultipartFile file) throws IOException {
        if(file==null)
            return ResultData.fail("失败");
        else{
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID()+suffixName;
            file.transferTo(new File(realPath+fileName));
            String url="http://localhost:8081"+mapperPath+fileName;
            return ResultData.success(url);
        }
    }

    @RequestMapping("/saveCar")
    @isCheckToken
    public ResultData<String> saveCar(@RequestBody Car car){
        return carService.saveCar(car);
    }

    @RequestMapping("/updateCar")
    @isCheckToken
    public ResultData<String> updateCar(@RequestBody UpdateCarQo qo){return carService.update(qo);}

    @RequestMapping("/deleteCar")
    @isCheckToken
    public ResultData<String> deleteCar(@RequestBody Car car){return carService.delete(car);}

    @RequestMapping("/deleteCarList")
    @isCheckToken
    public ResultData<String> deleteCarList(@RequestBody List<Car> list){return carService.deleteList(list);}

    @RequestMapping("/getCarInfo")
    public ResultData<Car> getCarInfo(String number){return carService.getCarInfo(number);}
}
