package com.sp.fc.web.Service;

import org.springframework.stereotype.Service;

@Service
public class SecurityMessageService {

//    @PreAuthorize("hasRole('ADMIN')")
    public String message(String name) {
        return name;
    }

}
