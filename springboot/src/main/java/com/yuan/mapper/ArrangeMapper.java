package com.yuan.mapper;

import com.yuan.domain.Arrange;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ArrangeMapper {

    //查询某个教练当月的排班记录
    List<Arrange> getArrangeList(Integer id, String date);
    //保存排班记录
    Integer saveArrangeList(List<Arrange> list);
    //更新一条排班记录
    Integer updateArrange(Arrange arrange);
    // 删除某个教练某个月的排班记录
    Integer deleteArrange(Integer id,String date);
    // 删除某个教练全部排班记录
    Integer deleteArrangeAll(Integer id);
    // 批量删除
    Integer deleteArrangeList(List<Integer> list);
}
