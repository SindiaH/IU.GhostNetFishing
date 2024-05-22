package com.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.org.apache.bcel.internal.Const;
import entities.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@ManagedBean
@ViewScoped
public class AuthCookieService {
    
    public static int MAX_AGE = 31536000;
    public static String AUTH_COOKIE_NAME = "authValue";
    

    public static void saveUserToCookie(User user) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", AuthCookieService.MAX_AGE);
        properties.put("path", "/");

        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(AuthCookieService.AUTH_COOKIE_NAME, jsonify(user), properties);
    }

    public static User getUserFromCookie() {
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(AuthCookieService.AUTH_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }
        User user = objectify(cookie.getValue());
        if (user == null) {
            return null;
        }
        System.out.println(user);
        return user;
    }
    
    public static void deleteAuthCookie() {
        try {
            Map<String, Object> properties = new HashMap<>();
            properties.put("maxAge", 0);
            properties.put("path", "/");
            FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(AuthCookieService.AUTH_COOKIE_NAME, "", properties);
        } catch (Exception e) {
            System.out.println("Error while deleting auth cookie: " + e);
        }
    }

    private static String jsonify(User user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            user.Password = "";
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static User objectify(String user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(user, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
