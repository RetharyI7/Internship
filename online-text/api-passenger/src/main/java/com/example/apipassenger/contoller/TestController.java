package com.example.apipassenger.contoller;


import com.example.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/authToken")
    public ResponseResult authToken(){
        return ResponseResult.success("auth   test");
    }

    @GetMapping("/noAuthToken")
    public ResponseResult noAuthToken(){
        return ResponseResult.success("no   auth   test");
    }
}
