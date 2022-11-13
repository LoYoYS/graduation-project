package com.yuan.service;

import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.Student;
import com.yuan.qo.StudentQo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public interface StudentService {
    PageInfo<Student> list(StudentQo qo);
    ResultData<String> insert(Student student);
    ResultData<String> updateStudent(Student student);
    ResultData<String> delete(Student student);
    ResultData<String> deleteList(List<Student>studentList);
    ResultData<String> importExcel(MultipartFile file) throws IOException, ParseException;
    Workbook exportExcel();
}
