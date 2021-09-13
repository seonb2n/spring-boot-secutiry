package com.sp.fc.web.config;

import com.sp.fc.web.controller.PaperController;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class CustomMetaDataSource implements MethodSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
        if(method.getName().equals("getPapersByPrimary") && targetClass == PaperController.class) {
            return List.of(new SecurityConfig("SCHOOL_PRIMARY"));
            //조건에 맞는 경우, securityConfig 로 school_primary 라는 토큰 발사
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        //methodInvocation 에 대해서 동작하도록 설정
        return MethodInvocation.class.isAssignableFrom(clazz);
    }
}
