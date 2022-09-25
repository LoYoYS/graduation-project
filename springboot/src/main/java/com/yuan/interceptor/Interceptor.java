package com.yuan.interceptor;

import com.yuan.configure.isCheckToken;
import com.yuan.utils.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //检查是否有isCheckToken注释，有则需要认证token
        if (method.isAnnotationPresent(isCheckToken.class)) {
            isCheckToken isCheckToken = method.getAnnotation(isCheckToken.class);
            if (isCheckToken.required()) {
                if(token == null){
                    throw new RuntimeException("请登入!");
                }
                else{
                    boolean flag = JwtUtil.verifyToken(token);
                    if(flag){
                        return true;
                    }
                    else{
                        throw new RuntimeException("token无效或已过期，请重新登入!");
                    }
                }
            }
        }
        else{
            return  true;
        }
        return true;
    }
}
