package com.sp.fc.web.teacher;

import com.sp.fc.web.student.Student;
import com.sp.fc.web.student.StudentAuthenticationToken;
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
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        if(teacherDB.containsKey(token.getName())) {
            Teacher teacher = teacherDB.get(token.getName());
            return TeacherAuthenticationToken.builder()
                    .principal(teacher)
                    .details(teacher.getUsername())
                    .authenticated(true)
                    .build();
        }
        return null;
        //인증에 실패한, 처리할 수 없는 authentication 은 null 로 넘겨야 다른 filter 에서 처리가 된다.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
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
