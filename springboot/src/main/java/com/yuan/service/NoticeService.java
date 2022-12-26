package com.yuan.service;

import com.github.pagehelper.PageInfo;
import com.yuan.domain.Notice;
import com.yuan.domain.NumberCount;
import com.yuan.domain.ResultData;

import java.util.List;

public interface NoticeService {
    PageInfo<Notice> list(Integer page);
    ResultData<List<Notice>> getList();
    PageInfo<Notice> getNotices();
    ResultData<String> save(Notice notice);
    ResultData<String> update(Notice notice);
    ResultData<Notice> getNotice(Integer id);
    ResultData<String> delete(Integer id);
    ResultData<NumberCount> getNumber();
}
