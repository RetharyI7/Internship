package com.example.apipassenger.remote;

import com.example.internalcommon.dto.ResponseResult;
import com.example.internalcommon.request.VerificationCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface UserClient {

    @RequestMapping(method = RequestMethod.POST,value = "/user")
    ResponseResult getUser (@RequestBody VerificationCodeDto verificationCodeDto);

}
