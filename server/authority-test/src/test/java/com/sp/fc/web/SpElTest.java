package com.sp.fc.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Person{
    private String name;
    private int height;

    public boolean over(int pivot) {
        return height >= pivot;
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Horse{
    private String name;
    private int height;

    public boolean over(int pivot) {
        return height >= pivot;
    }

}

@SpringBootTest
public class SpElTest {

    ExpressionParser parser = new SpelExpressionParser();

    Person person = Person.builder()
            .name("홍길동")
            .height(175)
            .build();

    Horse horse = Horse.builder()
            .name("nancy")
            .height(210)
            .build();

    @Test
    void test_1() {
        assertEquals("홍길동", parser.parseExpression("name").getValue(person));
        assertTrue(parser.parseExpression("over(170)").getValue(person, Boolean.class));
    }

    @Test
    void test_2() {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setBeanResolver(new BeanResolver() {
            @Override
            public Object resolve(EvaluationContext context, String beanName) throws AccessException {
                return beanName.equals("person") ? person : horse;
            }
        });

        assertTrue(parser.parseExpression("@person.over(170)").getValue(ctx, Boolean.class));

    }

}
