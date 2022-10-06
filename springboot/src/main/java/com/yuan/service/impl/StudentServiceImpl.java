package com.yuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.Student;
import com.yuan.mapper.StudentMapper;
import com.yuan.mapper.SubjectMapper;
import com.yuan.qo.StudentQo;
import com.yuan.qo.UpdateStudentQo;
import com.yuan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public PageInfo<Student> list(StudentQo studentQo) {
        PageHelper.startPage(studentQo.getCurrentPage(),studentQo.getSize());
        List<Student> list = studentMapper.list(studentQo);
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public ResultData<String> insert(Student student) {
        Student s = studentMapper.findAllById_number(student);
        if (s != null)
            return ResultData.fail("学员档案已经存在");
        else{
            Integer insert = studentMapper.insert(student);
            Integer insert1 = subjectMapper.insert(student);
            if (0<insert&&0<insert1)
                return ResultData.success("添加成功!");
            else
                return ResultData.fail("添加失败!");
        }
    }

    @Override
    public ResultData<String> updateStudent(UpdateStudentQo qo) {
        //  删除原有科目记录
        Integer delete = subjectMapper.delete(qo.getOldStudent());
        //  插入新的科目记录
        Integer insert = subjectMapper.insert(qo.getNewStudent());
        //  更新学员信息
        Integer integer = studentMapper.updateStudent(qo.getNewStudent());
        if(0<delete && 0<insert && 0<integer)
            return ResultData.success("修改成功");
        else
            return ResultData.fail("修改失败");
    }

    @Override
    public ResultData<String> delete(Student student) {
        Integer delete = studentMapper.delete(student);
        Integer delete1 = subjectMapper.delete(student);
        if(0<delete && 0<delete1)
            return ResultData.success("删除成功");
        else
            return ResultData.fail("删除失败");
    }

    @Override
    public ResultData<String> deleteList(List<Student> studentList) {
        Integer integer = subjectMapper.deleteList(studentList);
        Integer integer1 = studentMapper.deleteList(studentList);
        if(0<integer && 0<integer1)
            return ResultData.success("删除成功!");
        else
            return ResultData.fail("删除失败!");
    }
}
