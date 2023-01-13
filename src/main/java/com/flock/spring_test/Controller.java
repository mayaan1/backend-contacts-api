package com.flock.spring_test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/status")
    public String status() {
        System.out.println("API Called");
        return "return API\n";
    }
}
