package com.yuan.service;


import com.yuan.domain.ResultData;
import com.yuan.domain.User;
import com.yuan.qo.UpdatePasswordQo;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;


public interface UserService {
     ResultData<User> login(User user);
     String sendCode(String email) throws MessagingException;
     ResultData<String> forgetPassword(String email, String password, String code, String code1);
     ResultData<User> wx_login(String code);
     ResultData<String> update(User user);
     ResultData<String> updatePassword(UpdatePasswordQo qo);
     ResultData<String> updateAvatar(MultipartFile file, Integer id);
}
