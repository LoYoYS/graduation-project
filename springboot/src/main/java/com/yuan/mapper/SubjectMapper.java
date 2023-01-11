package com.yuan.mapper;

import com.yuan.domain.Student;
import com.yuan.domain.SubjectApply;
import com.yuan.qo.StudentQo;
import com.yuan.qo.VacateQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {
    Integer insert(Student student);
    Integer update(Student student);
    Integer delete(Student student);
    Integer deleteList(List<Student> studentList);
    //保存科目申请
    Integer saveApply(SubjectApply apply);
    //查询是否存在未处理申请
    Integer findApply(SubjectApply apply);
    //学员查看科目申请列表
    List<SubjectApply>getApplicationListById(Integer id);
    //撤销科目申请
    Integer cancelApply(Integer id);
    //管理员获取申请列表
    List<SubjectApply> getList(VacateQo qo);
    //审批申请
    Integer updateApply(SubjectApply apply);
    //更新科目一
    Integer updateSubjectA(Integer id);
    Integer updateSubjectB(Integer id);
    Integer updateSubjectC(Integer id);
    Integer updateSubjectD(Integer id);

}
