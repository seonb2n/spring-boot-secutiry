package com.sp.fc.web.config;

import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private MethodInvocation invocation;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        super(authentication);
        this.invocation = invocation;
    }

    private Object filterObject;
    private Object returnObject;

    //SecurityExpressionRoot 를 상속받고, 거기서 학생 role 인지 검증하는 메소드 생성
    public boolean isStudent() {
        return getAuthentication().getAuthorities().stream().filter(
                a -> a.getAuthority().equals("ROLE_STUDENT"))
                .findAny().isPresent();
    }

    public boolean isTutor() {
        return getAuthentication().getAuthorities().stream().filter(
                a -> a.getAuthority().equals("ROLE_TUTOR"))
                .findAny().isPresent();
    }

    @Override
    public Object getThis() {
        return this;
    }
}
