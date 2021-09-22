package com.sp.fc.user.service;

import com.sp.fc.user.domain.School;
import com.sp.fc.user.service.helper.SchoolTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class SchoolTest {

    @Autowired
    private SchoolRepository schoolRepository;

    private SchoolService schoolService;
    private SchoolTestHelper schoolTestHelper;

    School school;

    @BeforeEach
    void before() {
        this.schoolRepository.deleteAll();
        this.schoolService = new SchoolService(schoolRepository);
        this.schoolTestHelper = new SchoolTestHelper(schoolService);
        school = this.schoolTestHelper.createSchool("테스트 학교", "서울");
    }

    @DisplayName("Create School Test")
    @Test
    void test_1() {
        schoolTestHelper.assertSchool(school, "테스트 학교", "서울");
    }

    @Test
    void test_2() {
        schoolService.updateName(school.getSchoolId(), "test 2 school");
        schoolTestHelper.assertSchool(school, "test 2 school", "서울");
    }

    @Test
    void test_3() {

        schoolTestHelper.createSchool("부산 학교", "부산");

        List<String> list = schoolService.cities();
        assertEquals(2, list.size());
    }

    @Test
    void test_4() {
        List<School> list = schoolService.findAllByCity("서울");
        assertEquals(1, list.size());

        schoolTestHelper.createSchool("서울 학교2", "서울");
        list = schoolService.findAllByCity("서울");
        assertEquals(2, list.size());

    }


}
