package com.sp.fc.web.student;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class StudentManager implements AuthenticationProvider, InitializingBean {

    private HashMap<String, Student> studentDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;
        if(studentDB.containsKey(token.getCredentials())) {
            Student student = studentDB.get(token.getCredentials());
            return StudentAuthenticationToken.builder()
                    .principal(student)
                    .details(student.getUsername())
                    .authenticated(true)
                    .build();
        }
        return null;
        //인증에 실패한, 처리할 수 없는 authentication 은 null 로 넘겨야 다른 filter 에서 처리가 된다.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == StudentAuthenticationToken.class;
        //해당 형태의 token 을 받으면, 검증을 하는 provider 로 기능을 하겠다는 선언
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Bean 이 초기화 될때 작동.
        Set.of(
                new Student("hong", "홍진호", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
                new Student("kim", "김택용", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
                new Student("lee", "이영호", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")))
        ).forEach(s -> studentDB.put(s.getId(), s));
    }
}
