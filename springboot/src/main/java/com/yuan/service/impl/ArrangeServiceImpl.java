package com.yuan.service.impl;

import com.yuan.domain.Arrange;
import com.yuan.domain.Interval;
import com.yuan.domain.ResultData;
import com.yuan.mapper.ArrangeMapper;
import com.yuan.service.ArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArrangeServiceImpl implements ArrangeService {
    
    @Autowired
    private ArrangeMapper mapper;
    
    @Override
    public ResultData<List<Arrange>> getArrangeList(Integer id, String date) {
        return ResultData.success(mapper.getArrangeList(id,date));
    }

    @Override
    public ResultData<String> saveArrangeList(List<Arrange> list) {
        Integer integer=0;
        for (Arrange a:list){
             integer = mapper.saveArrangeList(a);
             for(Interval i:a.getList()){
                 i.setA_id(a.getId());
                 mapper.saveInterval(i);
             }
        }
        if(0<integer)
            return ResultData.success("保存成功！");
        return ResultData.fail("保存失败！");
    }

    @Override
    public ResultData<String> updateArrange(Arrange arrange) {
        Integer integer = mapper.updateArrange(arrange);
        if(0<integer)
            return ResultData.success("修改成功！");
        return ResultData.fail("修改失败！");
    }

    @Override
    public ResultData<String> updateInterval(List<Interval> list) {
        mapper.deleteInterval(list.get(0).getA_id());
        Integer integer = mapper.updateInterval(list);
        if(0<integer)
            return ResultData.success("修改成功！");
        return ResultData.fail("修改失败！");
    }

    @Override
    public ResultData<String> deleteArrange(List<Arrange> list) {
        Integer integer = mapper.deleteArrange(list.get(0).getC_id(),list.get(0).getDate());
        for (Arrange a:list){
            mapper.deleteInterval(a.getId());
        }
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<String> deleteArrangeAll(Integer id) {
        Integer integer = mapper.deleteArrangeAll(id);
        mapper.deleteIntervalByCid(id);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败,当前教练已无排班记录！");
    }

    @Override
    public ResultData<String> deleteArrangeList(List<Integer> list) {
        Integer integer = mapper.deleteArrangeList(list);
        mapper.deleteAllInterval(list);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败，当前已无可删除记录！");
    }

    @Override
    public ResultData<List<Arrange>> getArranges(Integer id, String date) {
        return ResultData.success(mapper.getArranges(id,date));
    }
}
