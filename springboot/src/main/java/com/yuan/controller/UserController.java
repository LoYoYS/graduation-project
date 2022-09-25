package com.yuan.controller;

import com.yuan.domain.ResultData;
import com.yuan.domain.User;
import com.yuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
        return userService.selectUser(loginUser);
    }

    @RequestMapping("/sendCode")
    public ResultData<String> sendCode(String email,HttpServletRequest request) throws MessagingException {
        String code = userService.sendCode(email);
        request.getSession().setAttribute("code",code);
        return ResultData.success();
    }

    @RequestMapping("/update")
    public ResultData<String> forgetPassword(String email,String newPassword,String code,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String code1 = (String) session.getAttribute("code");
        session.removeAttribute("code");
        return userService.updatePassword(email, newPassword, code, code1);
    }

    @RequestMapping("/wx_login")
    public ResultData<User> wx_login(String code){
        System.out.println(code);
        return userService.wx_login(code);
    }
}
