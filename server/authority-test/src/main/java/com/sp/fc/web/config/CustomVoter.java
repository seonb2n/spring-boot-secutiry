package com.sp.fc.web.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public class CustomVoter implements AccessDecisionVoter<MethodInvocation> {

    private final String PREFIX = "SCHOOL_";

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute().startsWith(PREFIX);
        //prefix 로 시작하는 attribute 를 처리하겠다는 설정
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MethodInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public int vote(Authentication authentication, MethodInvocation object, Collection<ConfigAttribute> attributes) {
        String role = attributes.stream().filter(attr -> attr.getAttribute().startsWith(PREFIX))
                .map(attr->attr.getAttribute().substring(PREFIX.length()))
                .findFirst().orElseGet(()->null);
        if(role != null && authentication.getAuthorities().stream().filter(auth -> auth.getAuthority().equals("ROLE_"+role.toUpperCase()))
            .findAny().isPresent()) {
            return ACCESS_GRANTED;
        } //school_primary 로 들어온 attribute 에 대해서, role_primary 로 바꿔서 맞는지 확인함.
        return ACCESS_DENIED;
    }
}
