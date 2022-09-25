package com.yuan.service;


import com.yuan.domain.ResultData;
import com.yuan.domain.User;

import javax.mail.MessagingException;


public interface UserService {
     ResultData<User> selectUser(User user);
     String sendCode(String email) throws MessagingException;
     ResultData<String> updatePassword(String email, String password, String code, String code1);
     ResultData<User> wx_login(String code);
}
