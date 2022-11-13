package com.yuan.mapper;

import com.yuan.domain.Vacate;
import com.yuan.qo.VacateQo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface VacateMapper {

    List<Vacate> list(VacateQo qo);
    Integer check(Integer id,Integer status);
    Integer accessAll(List<Integer> list);
    Integer refuseAll(List<Integer> list);

    Integer submit(Vacate vacate);
    Integer find(Integer id);
    List<Vacate> getList(Integer id);
    Integer cancel(Integer id);
    Integer confirm(Integer id);
}