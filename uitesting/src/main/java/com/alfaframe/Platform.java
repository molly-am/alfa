package com.alfaframe;

import lombok.Getter;

@Getter
public enum Platform {
    ANDROID("android"),
    IOS("ios");

    private final String platform;

    private Platform(String platform){
        this.platform = platform;
    }
}
