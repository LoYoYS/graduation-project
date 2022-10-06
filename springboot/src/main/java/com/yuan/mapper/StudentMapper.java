package com.yuan.mapper;

import com.yuan.domain.Student;
import com.yuan.qo.StudentQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> list(StudentQo studentQo);
    Student findAllById_number(Student student);
    Integer insert(Student student);
    Integer updateStudent(Student student);
    Integer delete(Student student);
    Integer deleteList(List<Student>studentList);
}
