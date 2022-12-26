package com.yuan.mapper;

import com.yuan.domain.Arrange;
import com.yuan.domain.Interval;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ArrangeMapper {

    //查询某个教练当月的排班记录
    List<Arrange> getArrangeList(Integer id, String date);
    //保存排班记录
    Integer saveArrangeList(Arrange arrange);
    // 保存时间段
    Integer saveInterval(Interval interval);
    //更新一条排班记录
    Integer updateArrange(Arrange arrange);
    //更新一条排班记录的时间段
    Integer updateInterval(List<Interval> list);
    //删除一条排班记录的时间段
    Integer deleteInterval(Integer id);
    //删除某个教练所有的时间段
    Integer deleteIntervalByCid(Integer id);
    //删除所有教练所有的时间段
    Integer deleteAllInterval(List<Integer> list);
    // 删除某个教练某个月的排班记录
    Integer deleteArrange(Integer id,String date);
    // 删除某个教练全部排班记录
    Integer deleteArrangeAll(Integer id);
    // 批量删除
    Integer deleteArrangeList(List<Integer> list);
    //教练查看排班情况
    List<Arrange> getArranges(Integer id, String date);
}
