package com.yuan.mapper;

import com.yuan.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User select(User user);
    User selectByEmail(String email);
    void updatePassword(User user);
    User findUserByUsername(String userName);
    void insertUser(User user);
}
