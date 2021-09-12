package com.sp.fc.web.config;

import com.sp.fc.web.Service.Paper;
import com.sp.fc.web.Service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private PaperService paperService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {
        Paper paper = paperService.getPaper((long)targetId);
        if (paper == null) throw new AccessDeniedException("There is no Paper");

        if (authentication.getAuthorities().stream().filter(
                o -> o.getAuthority().equals("ROLE_TUTOR")).findAny().isPresent()) {
            boolean canUse = paper.getTutorId().equals(authentication.getName());
            return canUse;
        }

        if (paper.getState() == Paper.State.PREPARE) return false;

        if (authentication.getAuthorities().stream().filter(
                o -> o.getAuthority().equals("ROLE_STUDENT")).findAny().isPresent()) {
            boolean canUse = paper.getStudentIds().stream().filter(userId -> userId.equals(authentication.getName()))
                    .findAny().isPresent();
            //paper 에 등록되어 있는 학생만 가져올 수 있음
            return canUse;
        }

        return false;

    }
}
