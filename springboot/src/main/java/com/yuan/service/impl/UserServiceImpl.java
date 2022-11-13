package com.yuan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.domain.*;
import com.yuan.mapper.CoachMapper;
import com.yuan.mapper.StudentMapper;
import com.yuan.mapper.UserMapper;
import com.yuan.qo.IdentifyQo;
import com.yuan.qo.UpdatePasswordQo;
import com.yuan.qo.UserQo;
import com.yuan.service.UserService;
import com.yuan.utils.EmailUtil;
import com.yuan.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CoachMapper coachMapper;

    @Resource
    JavaMailSenderImpl mailSender;

    @Value("${Location.realPath}")
    private String realPath;
    @Value("${Location.mapperPath}")
    private String mapperPath;

    @Override
    public ResultData<User> login(User user) {
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
    public ResultData<String> forgetPassword(String email, String password, String code, String code1) {
        User user = userMapper.selectByEmail(email);
        if (code.equals(code1)){
            if (user==null){
                return ResultData.fail("该邮箱不存在!");
            }
            else{
                user.setPassword(password);
                userMapper.forgetPassword(user);
                return ResultData.success("保存成功!");
            }
        }
        else
            return ResultData.fail("验证码错误!");
    }

    @Override
    public ResultData<String> update(User user) {
        int result=userMapper.updateUser(user);
        if(result>0){
            return ResultData.success("修改成功");
        }
        return ResultData.fail("修改失败");
    }

    @Override
    public ResultData<String> updatePassword(UpdatePasswordQo qo) {
        User user = userMapper.selectByPassword(qo);
        if(user==null)
            return ResultData.fail("旧密码不正确");
        else{
            Integer integer = userMapper.updatePassword(qo);
            if(integer>0)
                return ResultData.success("修改成功");
            else
                return ResultData.fail("修改失败");
        }
    }

    @Override
    public ResultData<String> updateAvatar(MultipartFile file, Integer id) {
        String fileName = file.getOriginalFilename();
        if(fileName!=null){
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID()+suffixName;
        }
        try {
            file.transferTo(new File(realPath+fileName));
            String url="http://localhost:8081"+mapperPath+fileName;
            userMapper.updateUserAvatar(id,url);
            return ResultData.success(url);
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.fail("更换失败");
        }
    }

    @Override
    public ResultData<PageInfo<Wx_user>> list(UserQo qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getSize());
        List<Wx_user> list = userMapper.list(qo);
        return ResultData.success(new PageInfo<>(list));
    }



    /*
    * 小程序端
    * */
    @Override
    public ResultData<Wx_user> wx_login(Wx_user user) {
        String url ="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=jsCode&grant_type=authorization_code";
        String requestUrl = url.replace("APPID", "wx1924d4cf20af38ff").replace
                ("SECRET", "65f6929c3316237fa75434f66c83f579").replace("jsCode", user.getUsername());
        JSONObject jsonObject;
        String  value=Get(requestUrl);
        //解析返回值，对象解析成字符串
        jsonObject= (JSONObject) JSON.parse(value);
        String openid= (String) jsonObject.get("openid");
        //String session_key= (String) jsonObject.get("session_key");
        Wx_user result = userMapper.findUserByUsername(openid);
        if(result==null){
            user.setUsername(openid);
            userMapper.insertUser(user);
            return ResultData.success(user);
        }
        return ResultData.success(result);
    }

    public ResultData<Wx_user> refresh(Wx_user user) {
        Wx_user result = userMapper.findUserByUsername(user.getUsername());
        return ResultData.success(result);
    }

    @Override
    public ResultData<Wx_user> identify(IdentifyQo qo) {
        Integer student = studentMapper.findStudentByNIW(qo);
        Integer coach = coachMapper.findCoachByNIW(qo);
        if(student==0 && coach==0)
            return ResultData.fail("身份信息不存在或已被绑定！请联系负责人。",userMapper.findUserById(qo.getId()));
        if(0<student){
            studentMapper.updateWx(qo);
            Integer s_id = studentMapper.selectStudent(qo);
            userMapper.updateRole(qo.getId(),s_id,1);
        }
        if(0<coach){
            coachMapper.updateWx(qo);
            Integer c_id = coachMapper.selectCoach(qo);
            userMapper.updateRole(qo.getId(), c_id,-1);
        }
        return ResultData.success("身份信息认证成功！",userMapper.findUserById(qo.getId()));
    }

    @Override
    public ResultData<String> unbind(Wx_user user) {
        Integer unbind = userMapper.unbind(user.getId());
        if (user.getRole()==1)
            studentMapper.unbind(user.getA_id());
        else if(user.getRole()==-1)
            coachMapper.unbind(user.getA_id());
        if(unbind>0)
            return ResultData.success("解绑成功！");
        return ResultData.fail("解绑失败！");
    }

    @Override
    public ResultData<?> getInfo(Integer id, Integer role) {
        if (role==1){
            Student info = studentMapper.getInfo(id);
            return ResultData.success(info);
        }
        if(role==-1){
            Coach info = coachMapper.getInfo(id);
            return ResultData.success(info);
        }
        return ResultData.success(null);
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
