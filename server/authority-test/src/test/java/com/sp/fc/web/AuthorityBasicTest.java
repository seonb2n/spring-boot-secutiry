package com.sp.fc.web;

import com.sp.fc.web.test.WebIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class  AuthorityBasicTest extends WebIntegrationTest {

    TestRestTemplate client;

    @Test
    public void test_1() {
        client = new TestRestTemplate("user1", "1111");
        ResponseEntity<String> responseEntity = client.getForEntity(uri("/greeting/sbsbsb"), String.class);

        assertEquals("hello sbsbsb", responseEntity.getBody());
        System.out.println(responseEntity.getBody());
    }

}
