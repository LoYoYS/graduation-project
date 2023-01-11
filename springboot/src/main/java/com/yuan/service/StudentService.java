package com.yuan.service;

import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.Student;
import com.yuan.domain.SubjectApply;
import com.yuan.qo.StudentQo;
import com.yuan.qo.VacateQo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface StudentService {
    PageInfo<Student> list(StudentQo qo);
    ResultData<String> insert(Student student);
    ResultData<String> updateStudent(Student student);
    ResultData<String> delete(Student student);
    ResultData<String> deleteList(List<Student>studentList);
    ResultData<String> importExcel(MultipartFile file);
    Workbook exportExcel();
    ResultData<List<Student>> findStudent(String keyWord);
    //保存科目申请
    ResultData<String> saveApply(SubjectApply apply);
    ResultData<List<SubjectApply>> getApplicationListById(Integer id);
    ResultData<String> cancelApply(Integer id);
    ResultData<PageInfo<SubjectApply>> getList(VacateQo qo);
    ResultData<String> check(SubjectApply apply);
}
