package com.orive.security.demo;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('MP')")
public class AdminController {

    @GetMapping
    @PreAuthorize("hasAuthority('mp:read')")
    public String get() {
        return "GET:: mp controller";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('mp:create')")
    @Hidden
    public String post() {
        return "POST:: mp controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('mp:update')")
    @Hidden
    public String put() {
        return "PUT:: mp controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('mp:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: mp controller";
    }
}
