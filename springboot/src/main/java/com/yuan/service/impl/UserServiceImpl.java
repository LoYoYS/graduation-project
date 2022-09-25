package com.yuan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuan.domain.ResultData;
import com.yuan.domain.User;
import com.yuan.mapper.UserMapper;
import com.yuan.service.UserService;
import com.yuan.utils.EmailUtil;
import com.yuan.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    JavaMailSenderImpl mailSender;

    @Override
    public ResultData<User> selectUser(User user) {
        User user1 = userMapper.select(user);
        if (user1!=null){
            String token = JwtUtil.getToken(user1.getUsername());
            user1.setToken(token);
            return ResultData.success("登入成功!",user1);
        }
        else
            return ResultData.fail("用户名或密码错误!",null);
    }

    @Override
    public String sendCode(String email) throws MessagingException {
        String code = new EmailUtil().getCode();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        System.out.println(mimeMessage);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        //标题
        helper.setSubject("本次验证码为："+code);
        //内容
        helper.setText("尊敬的用户你好，你的验证码为："+"<h2>"+code+"</h2>"+"打死也不要告诉别人哦！",true);
        //邮件接收者
        helper.setTo(email);
        //邮件发送者
        helper.setFrom("574064600@qq.com");
        mailSender.send(mimeMessage);
        return code;
    }

    @Override
    public ResultData<String> updatePassword(String email, String password, String code, String code1) {
        User user = userMapper.selectByEmail(email);
        if (code.equals(code1)){
            if (user==null){
                return ResultData.fail("该邮箱不存在!");
            }
            else{
                user.setPassword(password);
                userMapper.updatePassword(user);
                return ResultData.success("保存成功!");
            }
        }
        else
            return ResultData.fail("验证码错误!");
    }

    @Override
    public ResultData<User> wx_login(String code) {
        String url ="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=jsCode&grant_type=authorization_code";
        String requestUrl = url.replace("APPID", "wx1924d4cf20af38ff").replace("SECRET", "65f6929c3316237fa75434f66c83f579").replace("jsCode", code);
        JSONObject jsonObject;
        String  value=Get(requestUrl);
        //解析返回值，对象解析成字符串
        jsonObject= (JSONObject) JSON.parse(value);
        String openid= (String) jsonObject.get("openid");
        String session_key= (String) jsonObject.get("session_key");
        User result = userMapper.findUserByUsername(openid);
        if(result==null){
            User user = new User(null, openid, "123456", "", "", 1, "");
            userMapper.insertUser(user);
            return ResultData.success("新注册",user);
        }
        else{
            return ResultData.success("老用户",result);
        }
    }

    public String Get(String url){
        String result="";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try{
            //构造url对象
            URL realUrl = new URL(url);
            //获取一个对应该URL的URLConnection对象
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //关闭资源
        finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
