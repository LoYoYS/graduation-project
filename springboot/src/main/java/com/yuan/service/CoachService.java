package com.yuan.service;

import com.github.pagehelper.PageInfo;
import com.yuan.domain.Coach;
import com.yuan.domain.ResultData;
import com.yuan.qo.CoachQo;
import com.yuan.qo.UpdateCoachQo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CoachService {
    PageInfo<Coach> getList(CoachQo qo);
    ResultData<String> saveCoach(Coach coach);
    ResultData<String> updateCoach(UpdateCoachQo qo);
    ResultData<String> delete(Coach coach);
    ResultData<String> deleteList(List<Coach> list);
    //获取未分配车辆的教练
    ResultData<List<Coach>> getCoachList();

    //小程序模块
    ResultData<List<Coach>> getCoaches(String keyWord);

    ResultData<String> importExcel(MultipartFile file);

    Workbook exportExcel();
}
