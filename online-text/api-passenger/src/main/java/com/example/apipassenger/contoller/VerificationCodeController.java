package com.example.apipassenger.contoller;

import com.example.apipassenger.Service.VerificationCodeService;
import com.example.internalcommon.dto.ResponseResult;
import com.example.internalcommon.request.VerificationCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/verification_code")
    public ResponseResult VerificationCode(@RequestBody VerificationCodeDto verificationCodeDto) {
        // 拿到手机号
        String code =verificationCodeDto.getPhone();
        //把手机号传到service方法层里面
        String code1 = verificationCodeService.getCode(code);
        return ResponseResult.success(code1);
    }

    @PostMapping("verification_code-check")
    public ResponseResult VerificationCodeCheck(@RequestBody VerificationCodeDto verificationCodeDto) {
        String phone = verificationCodeDto.getPhone();
        String code =verificationCodeDto.getCode();
        ResponseResult token=verificationCodeService.getToken(phone,code);
        return token;
        }
}
