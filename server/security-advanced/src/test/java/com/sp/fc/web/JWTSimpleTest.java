package com.sp.fc.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JWTSimpleTest {

    private void printToken(String token) {
        String[] tokens = token.split("\\.");
        System.out.println("header: " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("body: " + new String(Base64.getDecoder().decode(tokens[1])));
    }


    @DisplayName("1. jjwt 를 이용한 토큰 테스트")
    @Test
    void test_1(){
        String okta_token = Jwts.builder().addClaims(
                Map.of("name", "seonbin", "price", 3000)
                ).signWith(SignatureAlgorithm.HS256, "seonbin")
                .compact();
        System.out.println(okta_token);
        printToken(okta_token);

        //해싱해서 키 값을 사용
        System.out.println(Jwts.parser().setSigningKey("seonbin").parseClaimsJws(okta_token));
    }


    @DisplayName("2. java-jwt 를 이용한 토큰 테스트")
    @Test
    void test_2() {
        String oauth0_token = JWT.create().withClaim("name", "seonbin").withClaim("rpice", 3000)
                .sign(Algorithm.HMAC256("seonbin"));
        System.out.println(oauth0_token);
        printToken(oauth0_token);

        //해싱하지 않고 그대로 키 값을 사용
        DecodedJWT verified = JWT.require(Algorithm.HMAC256("seonbin")).build().verify(oauth0_token);
        System.out.println(verified.getClass());
    }

    @DisplayName("3. java-jwt 와 jjwt 간에 디코딩 테스트")
    @Test
    void test_3() {

        byte[] SEC_KEY = DatatypeConverter.parseBase64Binary("seonbin");
        //컨버터로 생성된 시크릿 키

        String okta_token = Jwts.builder().addClaims(
                Map.of("name", "seonbin", "price", 3000)
        ).signWith(SignatureAlgorithm.HS256, "seonbin")
                .compact();

        //양쪽 모두 시크릿 키를 이용해서 디코딩을 하면 된다.
        DecodedJWT verified = JWT.require(Algorithm.HMAC256(SEC_KEY)).build().verify(okta_token);
        System.out.println(verified.getClaims());

        Jws<Claims> tokenInfo = Jwts.parser().setSigningKey(SEC_KEY).parseClaimsJws(okta_token);
        System.out.println(tokenInfo);
        //클라이언트가 달라도 알고리즘과 키 값이 일치하면 검증이 된다.
    }

    @DisplayName("4. 만료 시간 테스트")
    @Test
    void test_4() throws InterruptedException {

        final Algorithm AL = Algorithm.HMAC256("seonbin");

        String token = JWT.create().withSubject("seonbin123")
                .withNotBefore(new Date(System.currentTimeMillis()+1000))
                .withExpiresAt(new Date(System.currentTimeMillis()+3000))
                .sign(AL);

        //Thread.sleep(2000);
        try {DecodedJWT verify = JWT.require(AL).build().verify(token);
        System.out.println(verify.getClaims());
        }catch (Exception e) {
            System.out.println("유호하지 않은 토큰입니다.");
            DecodedJWT decode = JWT.decode(token);
            System.out.println(decode.getClaims());
        }
    }

}
