package com.sp.fc.web;

import com.sp.fc.web.student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomLoginAppTest {

    @LocalServerPort
    int port;

    TestRestTemplate testClient = new TestRestTemplate("lim", "1");

    @Test
    void test_1() {
        //lim:1 login, check student list
        ResponseEntity<List<Student>> resp = testClient.exchange("http://localhost:"+port+"/api/teacher/students",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                });

        System.out.println(resp.getBody());
    }
}