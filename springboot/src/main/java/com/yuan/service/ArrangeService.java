package com.yuan.service;

import com.yuan.domain.Arrange;
import com.yuan.domain.ResultData;

import java.util.List;

public interface ArrangeService {

    //查询某个教练当月的排班表
    ResultData<List<Arrange>> getArrangeList(Integer id, String date);
    //保存排班记录
    ResultData<String> saveArrangeList(List<Arrange> list);
    //更新一条排班记录
    ResultData<String> updateArrange(Arrange arrange);
    //删除排班记录
    ResultData<String> deleteArrange(Integer id,String date);
    //删除某个教练所有排班记录
    ResultData<String> deleteArrangeAll(Integer id);
    //批量删除
    ResultData<String> deleteArrangeList(List<Integer> list);
}
