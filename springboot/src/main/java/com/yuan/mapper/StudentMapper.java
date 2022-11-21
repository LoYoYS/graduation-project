package com.yuan.mapper;

import com.yuan.domain.Student;
import com.yuan.qo.IdentifyQo;
import com.yuan.qo.StudentQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> list(StudentQo studentQo);
    List<Student> selectAll();
    Student findAllById_number(Student student);
    Integer insert(Student student);
    Integer updateStudent(Student student);
    Integer delete(Student student);
    Integer deleteList(List<Student>studentList);

    //小程序端
    //查询身份是否已经被绑定
    Integer findStudentByNIW(IdentifyQo qo);
    //更新微信绑定信息
    Integer updateWx(IdentifyQo qo);
    //解绑
    void unbind(Integer id);
    //获取id
    Integer selectStudent(IdentifyQo qo);
    //获取信息
    Student getInfo(Integer id);
    //查找学员
    List<Student>findStudent(String keyWord);
}
