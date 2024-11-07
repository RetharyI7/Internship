package com.example.servicepassengeruser.service;

import com.example.internalcommon.dto.PassengerUser;
import com.example.internalcommon.dto.ResponseResult;
import com.example.servicepassengeruser.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public ResponseResult loginOrReg(String phone){
        // 查询数据库中是否已存在该用户
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", phone);
        List<PassengerUser> passengerUsers = userMapper.selectByMap(map);

        if (passengerUsers.isEmpty()) {
            // 如果用户不存在，插入新用户
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerGender((byte) 1);
            passengerUser.setState((byte) 1);
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerPhone(phone);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            passengerUser.setProfilePhone("");

            // 插入用户信息
            userMapper.insert(passengerUser);
        }

        return ResponseResult.success("User login or registration successful");
    }
}