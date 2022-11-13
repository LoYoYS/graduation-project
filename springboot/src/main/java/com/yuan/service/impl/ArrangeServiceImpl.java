package com.yuan.service.impl;

import com.yuan.domain.Arrange;
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
        Integer integer = mapper.saveArrangeList(list);
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
    public ResultData<String> deleteArrange(Integer id,String date) {
        Integer integer = mapper.deleteArrange(id, date);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<String> deleteArrangeAll(Integer id) {
        Integer integer = mapper.deleteArrangeAll(id);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<String> deleteArrangeList(List<Integer> list) {
        Integer integer = mapper.deleteArrangeList(list);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }
}
