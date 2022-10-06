package com.yuan.mapper;

import com.yuan.domain.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {
    Integer insert(Student student);
    Integer update(Student student);
    Integer delete(Student student);
    Integer deleteList(List<Student> studentList);
}
