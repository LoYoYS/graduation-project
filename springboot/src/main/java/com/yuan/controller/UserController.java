package com.yuan.controller;

import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.ResultData;
import com.yuan.domain.User;
import com.yuan.domain.Wx_user;
import com.yuan.qo.IdentifyQo;
import com.yuan.qo.UpdatePasswordQo;
import com.yuan.qo.UserQo;
import com.yuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public ResultData<User> login(@RequestBody User loginUser){
        return userService.login(loginUser);
    }

    @RequestMapping("/sendCode")
    public ResultData<String> sendCode(String email,HttpServletRequest request) throws MessagingException {
        String code = userService.sendCode(email);
        request.getSession().setAttribute("code",code);
        return ResultData.success();
    }

    @RequestMapping("/forgetPassword")
    public ResultData<String> forgetPassword(String email,String newPassword,String code,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code1 = (String) session.getAttribute("code");
        session.removeAttribute("code");
        return userService.forgetPassword(email, newPassword, code, code1);
    }

    @RequestMapping("/updateUser")
    @isCheckToken
    public ResultData<String> updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping("/updatePassword")
    @isCheckToken
    public ResultData<String> updatePassword(@RequestBody UpdatePasswordQo qo) {return userService.updatePassword(qo);}

    @RequestMapping("/avatar")
//    @isCheckToken
    public ResultData<String> avatar(@RequestParam("file") MultipartFile file,@RequestParam("id") Integer id){
        return userService.updateAvatar(file,id);
    }

    @RequestMapping("/list")
//    @isCheckToken
    public ResultData<PageInfo<Wx_user>> list(UserQo qo){
        return userService.list(qo);
    }

    //小程序端
    @RequestMapping("/wx_login")
    public ResultData<Wx_user> wx_login(@RequestBody Wx_user user){
        return userService.wx_login(user);
    }

    @RequestMapping("/refresh")
    public ResultData<Wx_user> refresh(@RequestBody Wx_user user){
        return userService.refresh(user);
    }

    @RequestMapping("/identify")
    public ResultData<Wx_user> identify(@RequestBody IdentifyQo qo){
        return userService.identify(qo);
    }

    @RequestMapping("/unbind")
    public ResultData<String> unbind(@RequestBody Wx_user user){
        return userService.unbind(user);
    }

    @RequestMapping("/getInfo")
    public ResultData<?> getInfo(@RequestParam("id") Integer id, @RequestParam("role") Integer role){
        return userService.getInfo(id,role);
    }

}
