package com.electrosoft.ldaplogin.controllers;

import java.util.stream.Collectors;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String method(@CurrentSecurityContext SecurityContext context) {
        return context.getAuthentication().getName();
    }

    @GetMapping("/typeof")
    public String method(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "user is anonymous";
        } else {
            return "user is not anonymous";
        }
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world";
    }

    @GetMapping("/user")
    public Authentication getLoggedUserDetail() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//get username
        String username = auth.getName();

// concat list of authorities to single string seperated by comma
        String authorityString = auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

// check if the user have authority -roleA
        String role = "role_A";
        boolean isCurrentUserInRole = auth
                .getAuthorities()
                .stream()
                .anyMatch(role::equals);

        return auth;
    }
}
