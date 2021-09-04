package com.sp.fc.web.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAuthenticationToken implements Authentication {

    private Student principal;
    private String credentials;
    private String details;
    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return principal == null ? new HashSet<>() : principal.getRole();
    } //어떤 권한을 가질 건지 설정.

    @Override
    public String getName() {
        return principal == null ? "" : principal.getUsername();
    }
}
