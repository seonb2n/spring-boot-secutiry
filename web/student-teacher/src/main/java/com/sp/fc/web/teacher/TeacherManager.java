package com.sp.fc.web.teacher;

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
public class TeacherManager implements AuthenticationProvider, InitializingBean {

    private HashMap<String, Teacher> teacherDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if(teacherDB.containsKey(token.getName())) {
                return getAuthenticationToken(token.getName());
            }
            return null;
        }
        TeacherAuthenticationToken token = (TeacherAuthenticationToken) authentication;
        if(teacherDB.containsKey(token.getCredentials())) {
            return getAuthenticationToken(token.getCredentials());
        }
        return null;
    }

    private TeacherAuthenticationToken getAuthenticationToken(String id) {
        Teacher teacher = teacherDB.get(id);
        return TeacherAuthenticationToken.builder()
                .principal(teacher)
                .details(teacher.getUsername())
                .authenticated(true)
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TeacherAuthenticationToken.class ||
                authentication == UsernamePasswordAuthenticationToken.class;
        //해당 형태의 token 을 받으면, 검증을 하는 provider 로 기능을 하겠다는 선언
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Bean 이 초기화 될때 작동.
        Set.of(
                new Teacher("lim", "임요한", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))),
                new Teacher("song", "송병구", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))),
                new Teacher("park", "박성준", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")))
        ).forEach(s -> teacherDB.put(s.getId(), s));
    }
}
