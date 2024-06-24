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
        return context.getAuthentication().getDetails().toString();
    }

    @GetMapping("/usertypeof")
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

        String username = auth.getName();
        System.out.println("El usuario es: " + username);

        String authority = "COMUNICACIONES";
        String authorityString = auth
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        System.out.println("authorityString = " + authorityString);
        System.out.println("A COMUNICACIONES,esta? = " + auth.getAuthorities().toString().contains(authority));

        boolean is = authorityString.contentEquals(authority);
        System.out.println("is = " + is);

        boolean isCurrentUserInRole = auth
                .getAuthorities()
                .stream()
                .anyMatch(authority::equals);
        System.out.println("isCurrentUserInRole = " + isCurrentUserInRole);
        
        return auth;
    }
}
