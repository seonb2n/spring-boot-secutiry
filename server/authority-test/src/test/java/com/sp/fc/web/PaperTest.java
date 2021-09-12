package com.sp.fc.web;

import com.sp.fc.web.Service.Paper;
import com.sp.fc.web.Service.PaperService;
import com.sp.fc.web.test.WebIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaperTest extends WebIntegrationTest {

    TestRestTemplate client = new TestRestTemplate();

    @Autowired
    private PaperService paperService;

    private Paper paper1 = Paper.builder()
            .paperId(1L)
            .title("시험지1")
            .tutorId("tutor1")
            .studentIds(List.of("user1"))
            .state(Paper.State.PREPARE)
            .build();

    private Paper paper2 = Paper.builder()
            .paperId(2L)
            .title("시험지2")
            .tutorId("tutor1")
            .studentIds(List.of("user2"))
            .state(Paper.State.PREPARE)
            .build();

    private Paper paper3 = Paper.builder()
            .paperId(3L)
            .title("시험지3")
            .tutorId("tutor1")
            .studentIds(List.of("user2"))
            .state(Paper.State.READY)
            .build();

    private Paper paper4 = Paper.builder()
            .paperId(4L)
            .title("시험지4")
            .tutorId("tutor1")
            .studentIds(List.of("user1"))
            .state(Paper.State.READY)
            .build();



    @Test
    void test_1() {
        //student 의 권한을 가진 사용자만 paper 를 조회할 수 있는 테스트
        paperService.setPaper(paper1);

        client = new TestRestTemplate("user1", "1111");
        ResponseEntity<List<Paper>> response = client.exchange(uri("/paper/mypapers"),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Paper>>() {
                });

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
        System.out.println(response.getBody());

    }

    @Test
    void test_1_1() {
        paperService.setPaper(paper1);
        paperService.setPaper(paper2);
        paperService.setPaper(paper3);
        paperService.setPaper(paper4);

        client = new TestRestTemplate("user2", "2222");
        ResponseEntity<List<Paper>> response = client.exchange(uri("/paper/mypapers2"),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Paper>>() {
                });

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        System.out.println(response.getBody());

    }

    @Test
    void test_2() {
        //user1은 user2의 paper 를 볼 수 없는 테스트
        paperService.setPaper(paper2);
        client = new TestRestTemplate("user1", "1111");
        ResponseEntity<Paper> response = client.exchange(uri("/paper/get/2"),
                HttpMethod.GET, null, new ParameterizedTypeReference<Paper>() {
                });

        assertEquals(403, response.getStatusCodeValue());

    }

    @Test
    void test_3() {
        //user2 도 prepare 상태인 paper 는 볼 수 없다
        paperService.setPaper(paper2);
        client = new TestRestTemplate("user2", "2222");
        ResponseEntity<Paper> response = client.exchange(uri("/paper/get/2"),
                HttpMethod.GET, null, new ParameterizedTypeReference<Paper>() {
                });

        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    void test_4() {
        //tutor 는 모든 페이퍼를 볼 수 있다.
        paperService.setPaper(paper2);
        client = new TestRestTemplate("tutor1", "1111");
        ResponseEntity<Paper> response = client.exchange(uri("/paper/get/2"),
                HttpMethod.GET, null, new ParameterizedTypeReference<Paper>() {
                });

        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    void test_5() {
        //user2 는 ready paper 을 볼 수 있다.
        paperService.setPaper(paper3);
        client = new TestRestTemplate("user2", "2222");
        ResponseEntity<Paper> response = client.exchange(uri("/paper/get/2"),
                HttpMethod.GET, null, new ParameterizedTypeReference<Paper>() {
                });

        assertEquals(200, response.getStatusCodeValue());
    }





}
