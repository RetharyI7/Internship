package com.example.internalcommon.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static  final String SIGN="$(kuabndlv)";

    public static final String JWT_KEY_PHONE="phone";

    public static final String JWT_KEY_IDENTITY="identity";

//    public static final String JWT_TOKEN_TYPE="token_type";

    public static final String JWT_TOKEN_TIME="token_time";




    public static String generatorToken(String phone,String identity){
        Map<String,String> map=new HashMap<>();
        map.put(JWT_KEY_PHONE,phone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());
        JWTCreator.Builder builder= JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return  sign;
    }


    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult  tokenResult=new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }



    public static TokenResult checkToken(String token){
        TokenResult tokenResult=null;
        try{
            tokenResult=JwtUtils.parseToken(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tokenResult;
    }
}
