package com.example.apipassenger.remote;

import com.example.internalcommon.dto.ResponseResult;
import com.example.internalcommon.respones.NumberCodeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-verification")
public interface ServiceVerificationCodeClient {

    @RequestMapping(method= RequestMethod.GET,value = "/number_code/{size}")
    ResponseResult<NumberCodeResponseDto> getNumberCode(@PathVariable("size") int size);
}
