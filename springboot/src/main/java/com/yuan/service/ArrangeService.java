package com.yuan.service;

import com.yuan.domain.Arrange;
import com.yuan.domain.Interval;
import com.yuan.domain.ResultData;

import java.util.List;

public interface ArrangeService {

    //查询某个教练当月的排班表
    ResultData<List<Arrange>> getArrangeList(Integer id, String date);
    //保存排班记录
    ResultData<String> saveArrangeList(List<Arrange> list);
    //更新某天排班
    ResultData<String> updateArrange(Arrange arrange);
    //更新某天排班的时间段
    ResultData<String> updateInterval(List<Interval> list);
    //删除某教练某月排班记录
    ResultData<String> deleteArrange(List<Arrange> list);
    //删除某个教练所有排班记录
    ResultData<String> deleteArrangeAll(Integer id);
    //全部删除
    ResultData<String> deleteArrangeList(List<Integer> list);
    // 教练获取排班情况
    ResultData<List<Arrange>> getArranges(Integer id, String date);

}
