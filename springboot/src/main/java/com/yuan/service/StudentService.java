package com.yuan.service;

import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.Student;
import com.yuan.qo.StudentQo;
import com.yuan.qo.UpdateStudentQo;

import java.util.List;


public interface StudentService {
    PageInfo<Student> list(StudentQo qo);
    ResultData<String> insert(Student student);
    ResultData<String> updateStudent(UpdateStudentQo qo);
    ResultData<String> delete(Student student);
    ResultData<String> deleteList(List<Student>studentList);

}
