package com.yuan.mapper;

import com.yuan.domain.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    Integer save(Notice notice);
    Integer update(Notice notice);
    Notice getNotice(Integer id);
    Integer delete(Integer id);
    List<Notice> list(Integer page);
    Integer getStudentCount();
    Integer getCoachCount();
    Integer getUserCount();
    Integer getCarCount();

}
