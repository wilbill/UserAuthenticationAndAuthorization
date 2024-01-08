package com.security.userauthenticationandauthorization.controller;

import org.springframework.web.bind.annotation.*;

//Controller accessed by management and admin only.
@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {
    //setting up our end points
    @GetMapping
    public String get(){
        return "GET:: management controller";
    }

    @PostMapping
    public String post(){
        return "POST:: management controller";
    }

    @PutMapping
    public String put(){
        return "Put:: management controller";
    }

    @DeleteMapping
    public String delete(){
        return "DELETE:: management controller";
    }
}
