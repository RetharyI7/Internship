package com.example.internalcommon.request;

import lombok.Data;

@Data
public class VerificationCodeDto {
    private String phone;
    private String code;

//    private String getPhone(){
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//
//    }
}
