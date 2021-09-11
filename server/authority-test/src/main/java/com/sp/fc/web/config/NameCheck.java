package com.sp.fc.web.config;

import org.springframework.stereotype.Component;

@Component
public class NameCheck {

    //이름을 검사해서 특정 이름일 때만 true 리턴
    public boolean check(String name) {
        return name.equals("sbsbsb");
    }
}
