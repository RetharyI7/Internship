package com.example.apipassenger.Service;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.example.apipassenger.remote.ServiceVerificationCodeClient;
import com.example.apipassenger.remote.UserClient;
import com.example.internalcommon.constant.CommonStatusEnum;
import com.example.internalcommon.constant.IdentityConstant;
import com.example.internalcommon.dto.ResponseResult;
import com.example.internalcommon.request.VerificationCodeDto;
import com.example.internalcommon.respones.NumberCodeResponseDto;
import com.example.internalcommon.util.JwtUtils;
import com.example.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;
    @Autowired
    private UserClient userClient;


    public String getCode(String phone) {
//        生成验证码
        ResponseResult<NumberCodeResponseDto> numberCode = serviceVerificationCodeClient.getNumberCode(6);
        int code = numberCode.getData().getCode();
        System.out.println("远程服务调用...............bbbbbbbb");
        // redis
        String generatorKey = RedisPrefixUtils.generatorKey(phone, IdentityConstant.PASSENGER_IDENTITY);
        stringRedisTemplate.opsForValue().set(generatorKey,code+"",5,TimeUnit.MINUTES);

//        stringRedisTemplate.opsForValue().set(generatorKey, String.valueOf(code), 2, TimeUnit.HOURS);

        //确认验证码存储到redis
        System.out.println("验证码存储到 Redis：" + code);
        System.out.println("存储使用的 Key：" + generatorKey);

        //短信
        return code+"";
    }

    public ResponseResult getToken(String phone, String code) {
        // 生成 Redis Key
        String generatorKey = RedisPrefixUtils.generatorKey(phone, IdentityConstant.PASSENGER_IDENTITY);

        // 从 Redis 中获取存储的验证码
        String numberCode = stringRedisTemplate.opsForValue().get(generatorKey);

        // 输出调试信息
        System.out.println("验证码从 Redis 中读取：" + numberCode);
        System.out.println("用户输入的验证码：" + code);

        // 检查验证码是否存在
        if (StringUtils.isBlank(numberCode)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE.getCode(), CommonStatusEnum.VERIFICATION_CODE.getValue());
        }

        // 比较用户输入的验证码和 Redis 中的验证码
        if (!code.equals(numberCode)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE.getCode(), CommonStatusEnum.VERIFICATION_CODE.getValue());
        }

        // 验证成功的其他业务逻辑
        VerificationCodeDto verificationCodeDto = new VerificationCodeDto();
        verificationCodeDto.setPhone(phone);
        userClient.getUser(verificationCodeDto);

        //生成Token
        //生成token
        String tokenKey = RedisPrefixUtils.generatorToken(phone, IdentityConstant.PASSENGER_IDENTITY);
        String token = JwtUtils.generatorToken(phone, IdentityConstant.PASSENGER_IDENTITY);
        stringRedisTemplate.opsForValue().set(tokenKey,token+"",30, TimeUnit.DAYS);
        //token------redis
        return ResponseResult.success(token);
    }

}
