package com.example.apipassenger.interceptor;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.example.internalcommon.dto.ResponseResult;
import com.example.internalcommon.dto.TokenResult;
import com.example.internalcommon.util.JwtUtils;
import com.example.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result=true;
        String resultString="";
        String authorization = request.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.checkToken(authorization);
        if(tokenResult==null){
            resultString="------ token invalid";
            result=false;
        }else{
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenPre = RedisPrefixUtils.generatorToken(phone, identity);
            String token = stringRedisTemplate.opsForValue().get(tokenPre);
            if((StringUtils.isBlank(token))||(!authorization.trim().equals(token.trim()))){
                resultString="token invalid";
                result=false;
            }
        }
//        if(!result){
//            PrintWriter out=response.getWriter();
//            out.print(JSONObject.fromObject(ResponseResult.fail(100,resultString)).toString());
//        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


}
