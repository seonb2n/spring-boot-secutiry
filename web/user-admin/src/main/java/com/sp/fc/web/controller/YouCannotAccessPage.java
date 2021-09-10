package com.sp.fc.web.controller;

import org.springframework.security.access.AccessDeniedException;

public class YouCannotAccessPage extends AccessDeniedException {
    public YouCannotAccessPage() {
        super("You Cannot access Page");
    }
}
