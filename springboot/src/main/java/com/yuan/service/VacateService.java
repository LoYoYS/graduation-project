package com.yuan.service;

import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.Vacate;
import com.yuan.qo.VacateQo;

import java.util.List;

public interface VacateService {

    ResultData<PageInfo<Vacate>>list(VacateQo qo);
    ResultData<String> check(Integer id,Integer status);
    ResultData<String> accessAll(List<Integer> list);
    ResultData<String> refuseAll(List<Integer> list);

    ResultData<String> submit(Vacate vacate);
    ResultData<List<Vacate>> getList(Integer id);
    ResultData<String> cancel(Integer id);
    ResultData<String> confirm(Integer id);

}
