package com.sp.fc.web.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.ArrayList;
import java.util.List;

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    //prePost 설정을 함으로써, method 에 대헌 globalMethodSecurityConfiguration 이 작동함.


    @Override
    protected AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        ExpressionBasedPreInvocationAdvice expressionAdvice = new ExpressionBasedPreInvocationAdvice();
        expressionAdvice.setExpressionHandler(getExpressionHandler());

        decisionVoters.add(new PreInvocationAuthorizationAdviceVoter(expressionAdvice));
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());
        //3개의 voter 를 만듦
        decisionVoters.add(new CustomVoter());
        //내가 만든 항상 찬성하는 voter 를 추가

        ConsensusBased committee = new ConsensusBased(decisionVoters);
        committee.setAllowIfEqualGrantedDeniedDecisions(false);

        //return new AffirmativeBased(decisionVoters);
        //긍정 위원회를 소집함

        return committee;
        //다수결 위원회 소집, 동수일때는 반대하도록 설정.
    }
}
