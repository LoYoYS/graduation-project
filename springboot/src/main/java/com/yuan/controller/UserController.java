package com.yuan.controller;

import com.yuan.configure.isCheckToken;
import com.yuan.domain.ResultData;
import com.yuan.domain.User;
import com.yuan.qo.UpdatePasswordQo;
import com.yuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public ResultData<User> login(@RequestBody User loginUser){
        return userService.login(loginUser);
    }

    @RequestMapping("/wx_login")
    public ResultData<User> wx_login(String code){
        System.out.println(code);
        return userService.wx_login(code);
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
    @isCheckToken
    public ResultData<String> avatar(@RequestParam("file") MultipartFile file,@RequestParam("id") Integer id){
        return userService.updateAvatar(file,id);
    }
}
