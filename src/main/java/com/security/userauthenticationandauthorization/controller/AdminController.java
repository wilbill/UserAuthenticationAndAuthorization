package com.security.userauthenticationandauthorization.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//Controller accessed by admin only.
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')") //works same way as commented matchers
public class AdminController {
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public String get(){
        return "GET:: admin controller";
    }

    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping
    public String post(){
        return "POST:: admin controller";
    }
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping
    public String put(){
        return "Put:: admin controller";
    }
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping
    public String delete(){
        return "DELETE:: admin controller";
    }
}
