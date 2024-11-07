package com.example.serviceverificationcode.controller;

import com.example.internalcommon.dto.ResponseResult;
import com.example.internalcommon.respones.NumberCodeResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/number_code/{size}")
    public ResponseResult  numberCode(@PathVariable("size") int size) {
            System.out.println("远程调用...........aaaaaaaaa");
        double code= (Math.random()*9+1)*(Math.pow(10,size-1));
        int code1 = (int) code;
        NumberCodeResponseDto numberCode = new NumberCodeResponseDto();
        numberCode.setCode(code1);
        return ResponseResult.success(numberCode);
    }
}
