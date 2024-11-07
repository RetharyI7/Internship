package com.example.servicepassengeruser.controller;

import com.example.internalcommon.dto.ResponseResult;
import com.example.internalcommon.request.VerificationCodeDto;
import com.example.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/user")
    public ResponseResult loginOrReg(@RequestBody VerificationCodeDto verificationCodeDto){
        return userService.loginOrReg(verificationCodeDto.getPhone());
    }

}
