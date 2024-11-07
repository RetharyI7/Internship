package com.example.internalcommon.constant;

import lombok.Getter;

public enum CommonStatusEnum {

    VERIFICATION_CODE(1000,"验证码不正确"),

    SUCCESS (1,"success"),

    fail( 0,"fail");

    @Getter
    private int code;
    @Getter
    private String value;

        CommonStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
}
