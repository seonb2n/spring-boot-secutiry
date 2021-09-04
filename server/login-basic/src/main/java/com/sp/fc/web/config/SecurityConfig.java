package com.sp.fc.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(1) //여러 filter chain 중에 어떤 filter 를 먼저 적용할지 정하는 @
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User.builder()
                .username("user2")
                .password(passwordEncoder().encode("2222"))
                .roles("USER")
                ).withUser(User.builder()
                .username("admin")
                .password(passwordEncoder().encode("3333"))
                .roles("ADMIN")
        );
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.antMatcher("/api/**");
//        //어떤 request 에 filter 를 적용할지 설정하는 antMatcher
//
//        http.authorizeRequests((requests) ->
//                requests.antMatchers("/").permitAll()
//                        .anyRequest().authenticated());
//        http.formLogin();
//        http.httpBasic();
//        //어떤 필터를 넣어서 작동시킬건지를 설정할 수 있다.

        http.headers().disable()
                .csrf().disable()
                .formLogin(login ->
                        login.defaultSuccessUrl("/", false)
                )
                .logout().disable()
                .requestCache().disable();
    }
}
