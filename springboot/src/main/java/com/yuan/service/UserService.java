package com.yuan.service;


import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.User;
import com.yuan.domain.Wx_user;
import com.yuan.qo.IdentifyQo;
import com.yuan.qo.UpdatePasswordQo;
import com.yuan.qo.UserQo;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.MessagingException;



public interface UserService {
     ResultData<User> login(User user);
     String sendCode(String email) throws MessagingException;
     ResultData<String> forgetPassword(String email, String password, String code, String code1);
     ResultData<String> update(User user);
     ResultData<String> updatePassword(UpdatePasswordQo qo);
     ResultData<String> updateAvatar(MultipartFile file, Integer id);
     ResultData<PageInfo<Wx_user>> list(UserQo qo);


     /*
     * 小程序
     * */
     //登入
     ResultData<Wx_user> wx_login(Wx_user user);
     //刷新信息
     ResultData<Wx_user> refresh(Wx_user user);
     //身份认证
     ResultData<Wx_user> identify(IdentifyQo qo);
     //解绑身份
     ResultData<String> unbind(Wx_user user);
     //获取绑定信息
     ResultData<?> getInfo(Integer id,Integer role);
}
