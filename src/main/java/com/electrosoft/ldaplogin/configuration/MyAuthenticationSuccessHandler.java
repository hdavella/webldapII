/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.electrosoft.ldaplogin.configuration;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author HDAVELLA
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException 
    {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN"))
        {
            request.getSession(false).setMaxInactiveInterval(30);
        }
        else
        {
            request.getSession(false).setMaxInactiveInterval(120);
        }
        //Your login success url goes here, currently login success url="/"
        response.sendRedirect("/user");//request.getContextPath()
    }
    
}
