package com.yuan.controller;

import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.ResultData;
import com.yuan.domain.Student;
import com.yuan.qo.StudentQo;
import com.yuan.qo.UpdateStudentQo;
import com.yuan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService service;

    @isCheckToken
    @RequestMapping("/list")
    public ResultData<PageInfo<Student>> list(StudentQo studentQo){
        System.out.println(studentQo);
        return ResultData.success(service.list(studentQo));
    }


    @RequestMapping("/save")
    @isCheckToken
    public ResultData<String> save(@RequestBody Student student){
//        System.out.println(student);
        return service.insert(student);
    }

    @RequestMapping("/update")
    @isCheckToken
    public ResultData<String> update(@RequestBody UpdateStudentQo qo){
//        System.out.println(qo);
        return service.updateStudent(qo);
    }

    @RequestMapping("/delete")
    @isCheckToken
    public ResultData<String> delete(@RequestBody Student student){
        System.out.println(student);
        return service.delete(student);
    }

    @RequestMapping("/deleteList")
    @isCheckToken
    public ResultData<String> deleteList(@RequestBody List<Student> studentList){
        System.out.println(studentList);
        return service.deleteList(studentList);
    }

}
