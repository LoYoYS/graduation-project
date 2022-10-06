package com.yuan.mapper;

import com.yuan.domain.User;
import com.yuan.qo.UpdatePasswordQo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User select(User user);
    User selectByPassword(UpdatePasswordQo qo);
    User selectByEmail(String email);
    void forgetPassword(User user);
    Integer updatePassword(UpdatePasswordQo qo);
    User findUserByUsername(String userName);
    void insertUser(User user);
    int updateUser(User user);
    int updateUserAvatar(Integer id,String url);
}
