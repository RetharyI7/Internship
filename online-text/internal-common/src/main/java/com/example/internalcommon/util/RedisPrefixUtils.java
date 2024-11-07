package com.example.internalcommon.util;

public class RedisPrefixUtils {


    public  static  String verificationCodePrefix="verification-code";

    public static String tokenPrefix="token-";

    public static String generatorKey(String phone,String identity) {
        return verificationCodePrefix + ":" + phone;
    }

    public static String generatorToken(String phone,String identity){

        return  tokenPrefix+"-"+identity+"-"+phone;
    }
}
