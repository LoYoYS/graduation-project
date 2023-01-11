package com.yuan.mapper;

import com.yuan.domain.Coach;
import com.yuan.domain.Student;
import com.yuan.domain.User;
import com.yuan.domain.Wx_user;
import com.yuan.qo.UpdatePasswordQo;
import com.yuan.qo.UserQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    //web端
    User select(User user);
    User selectByPassword(UpdatePasswordQo qo);
    User selectByEmail(String email);
    void forgetPassword(User user);
    Integer updatePassword(UpdatePasswordQo qo);
    int updateUser(User user);
    int updateUserAvatar(Integer id,String url);
    List<Wx_user> list(UserQo qo);
    //重置用户绑定的身份和角色
    Integer updateUserRole(Integer id,Integer role);
    //批量重置学员身份绑定
    Integer updateUserByStudentList(List<Student>list);
    //批量重置教练身份绑定
    Integer updateUserByCoachList(List<Coach>list);

    //小程序端
    Wx_user findUserByUsername(String userName);
    void insertUser(Wx_user user);
    Integer unbind(Integer id);
    Wx_user findUserById(Integer id);
    //更新用户角色
    Integer updateRole(Integer id,Integer a_id,Integer role);

}
