package com.sp.fc.web;

import com.sp.fc.user.domain.SpUser;
import com.sp.fc.user.repository.SpUserRepository;
import com.sp.fc.user.service.SpUserService;
import com.sp.fc.web.config.UserLoginForm;
import com.sp.fc.web.test.WebIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JWTReqeustTest extends WebIntegrationTest {

    @Autowired
    private SpUserRepository userRepository;

    @Autowired
    private SpUserService userService;

    @BeforeEach
    void before() {

        userRepository.deleteAll();
        SpUser user = userService.save(SpUser.builder()
                .email("user1")
                .password("1111")
                .enabled(true)
                .build());
        userService.addAuthority(user.getUserId(), "ROLE_USER");
    }

    @DisplayName("1. hello message 를 받아온다.")
    @Test
    void test_1() {

        RestTemplate client = new RestTemplate();

        HttpEntity<UserLoginForm> entity = new HttpEntity<>(
                UserLoginForm.builder()
                        .username("user1")
                        .password("1111")
                        .build()
        );

        ResponseEntity<SpUser> resp = client.exchange(uri("/login"), HttpMethod.POST, entity, SpUser.class);

        System.out.println(resp.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        System.out.println(resp.getBody());

    }

}
