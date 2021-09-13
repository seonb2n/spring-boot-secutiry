package com.sp.fc.web.controller;

import com.sp.fc.web.Service.Paper;
import com.sp.fc.web.Service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

//    @PreAuthorize("isStudent()")
    @PostFilter("notPrepareState(filterObject)")
    //paper 의 상태가 prepare 가 아니어야만 전송
    @GetMapping("/mypapers")
    public List<Paper> myPapers(@AuthenticationPrincipal User user) {
        return paperService.getMyPapers(user.getUsername());
    }

    @PostFilter("notPrepareState(filterObject) && filterObject.studentIds.contains(#user.username)")
    @GetMapping("/mypapers2")
    public List<Paper> myPapers2(@AuthenticationPrincipal User user) {
        return paperService.getMyPapers2(user.getUsername());

    }

    @PreAuthorize("hasPermission(#paperId, 'paper', 'read')")
//    @PostAuthorize("returnObject.studentIds.contains(#user.username)")
    @GetMapping("/get/{paperId}")
    public Paper getPaper(@AuthenticationPrincipal User user, @PathVariable Long paperId) {
        return paperService.getPaper(paperId);
    }

    @Secured({"SCHOOL_PRIMARY"})
    @GetMapping("/getPapersByPrimary")
    public List<Paper> getPapersByPrimary(@AuthenticationPrincipal User user) {
        return paperService.getAllPapers();
    }

}
