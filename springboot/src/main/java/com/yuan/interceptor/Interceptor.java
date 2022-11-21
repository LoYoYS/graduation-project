package com.yuan.interceptor;

import com.yuan.Excepetion.BusinessException;
import com.yuan.configure.isCheckToken;
import com.yuan.utils.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod=(HandlerMethod)handler;
            Method method=handlerMethod.getMethod();
            //检查是否有isCheckToken注释，有则需要认证token
            if (method.isAnnotationPresent(isCheckToken.class)) {
                isCheckToken isCheckToken = method.getAnnotation(isCheckToken.class);
                if (isCheckToken.required()) {
                    if(token == null || token.equals("")){
                        throw new BusinessException("9999","请登入!");
                    }
                    else{
                        boolean flag = JwtUtil.verifyToken(token);
                        if(flag){
                            String userName = JwtUtil.getUserName(token);
                            assert userName != null;
                            if(userName.equals("root"))
                                return true;
                            else
                                throw new BusinessException("5005","权限不足，操作失败");
                        }
                        else{
                            throw new BusinessException("9999","token无效或已过期，请重新登入!");
                        }
                    }
                }
            }
        }else if(handler instanceof ResourceHttpRequestHandler)
            return true;
        return true;
    }
}
