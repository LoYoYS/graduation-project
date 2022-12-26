package com.yuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.Vacate;
import com.yuan.mapper.VacateMapper;
import com.yuan.qo.VacateQo;
import com.yuan.service.VacateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacateServiceImpl implements VacateService {

    @Autowired
    private VacateMapper mapper;


    @Override
    public ResultData<PageInfo<Vacate>> list(VacateQo qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getSize());
        List<Vacate> list = mapper.list(qo);
        return ResultData.success(new PageInfo<>(list));
    }

    @Override
    public ResultData<String> check(Integer id, Integer status) {
        Integer flag = mapper.check(id,status);
        if(flag>0)
            return ResultData.success("操作成功！");
        return ResultData.fail("操作失败！");
    }

    @Override
    public ResultData<String> accessAll(List<Integer> list) {
        Integer flag = mapper.accessAll(list);
        if(flag>0)
            return ResultData.success("操作成功！");
        return ResultData.fail("操作失败！");
    }

    @Override
    public ResultData<String> refuseAll(List<Integer> list) {
        Integer flag = mapper.refuseAll(list);
        if(flag>0)
            return ResultData.success("操作成功！");
        return ResultData.fail("操作失败！");
    }

    @Override
    public ResultData<String> submit(Vacate vacate) {
        Integer integer = mapper.find(vacate.getC_id());
        if(integer>0){
            return ResultData.fail("还有待审批或待销假记录,请处理后再来！");
        }
        Integer submit = mapper.submit(vacate);
        if(submit>0)
            return ResultData.success("申请成功！");
        return ResultData.fail("申请失败！");
    }

    @Override
    public ResultData<List<Vacate>> getList(Integer id) {
        List<Vacate> list = mapper.getList(id);
        return ResultData.success(list);
    }

    @Override
    public ResultData<String> cancel(Integer id) {
        Integer cancel = mapper.cancel(id);
        if(cancel>0)
            return ResultData.success("撤销成功！");
        return ResultData.fail("撤销失败！");
    }

    @Override
    public ResultData<String> confirm(Integer id) {
        Integer cancel = mapper.confirm(id);
        if(cancel>0)
            return ResultData.success("销假成功！");
        return ResultData.fail("销假失败！");
    }
}
