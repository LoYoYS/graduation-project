package com.yuan.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.domain.Notice;
import com.yuan.domain.NumberCount;
import com.yuan.domain.ResultData;
import com.yuan.mapper.NoticeMapper;
import com.yuan.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper mapper;

    @Override
    public PageInfo<Notice> list(Integer page) {
        PageHelper.startPage(page,5);
        List<Notice> list = mapper.list(page);
        return new PageInfo<>(list);
    }

    @Override
    public ResultData<String> save(Notice notice) {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = dateFormat.format(date);
        notice.setTime(format);
        Integer save = mapper.save(notice);
        if(0<save)
            return ResultData.success("发布成功！");
        else
            return ResultData.fail("发布失败！");
    }

    @Override
    public ResultData<String> update(Notice notice) {
        Integer update = mapper.update(notice);
        if(0<update)
            return ResultData.success("修改成功！");
        return ResultData.fail("修改失败！");
    }

    @Override
    public ResultData<Notice> getNotice(Integer id) {
        Notice notice = mapper.getNotice(id);
        if(notice!=null)
            return ResultData.success(notice);
        return ResultData.fail(null);
    }

    @Override
    public ResultData<String> delete(Integer id) {
        Integer delete = mapper.delete(id);
        if(0<delete)
             return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<NumberCount> getNumber() {
        Integer studentCount = mapper.getStudentCount();
        Integer coachCount = mapper.getCoachCount();
        Integer userCount = mapper.getUserCount();
        Integer carCount = mapper.getCarCount();
        NumberCount numberCount = new NumberCount(studentCount, coachCount, userCount, carCount);
        return ResultData.success(numberCount);
    }

}
