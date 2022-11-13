package com.yuan.mapper;

import com.yuan.domain.Car;
import com.yuan.domain.Coach;
import com.yuan.qo.CoachQo;
import com.yuan.qo.IdentifyQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoachMapper {
    //获取教练列表
    List<Coach> getList(CoachQo qo);
    //获取全部教练
    List<Coach> selectAll();
    //获取还未分配车辆的教练
    List<Coach> getCoachList();
    //通过身份证号查找教练是否存在
    Coach findById_number(String id_number);
    //保存教练
    Integer save(Coach coach);
    //更新教练
    Integer update(Coach coach);
    Integer updateCar(String car,Integer id);
    Integer updateList(List<Car> list);
    Integer delete(Coach coach);
    Integer deleteList(List<Coach> list);

    //小程序模块
    Integer findCoachByNIW(IdentifyQo qo);
    //更新绑定信息
    Integer updateWx(IdentifyQo qo);
    //解绑
    void unbind(Integer id);
    //获取id
    Integer selectCoach(IdentifyQo qo);
    //获取信息
    Coach getInfo(Integer id);
    List<Coach> getCoaches(String keyWord);
}
