package com.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.helper.CryptHelper;
import dtos.AuthCookieDto;
import entities.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthCookieService {
    public static int MAX_AGE = 43200;
    public static String AUTH_COOKIE_NAME = "authValue";

    public static void saveUserToCookie(User user) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", AuthCookieService.MAX_AGE);
        properties.put("path", "/");

        FacesContext.getCurrentInstance().getExternalContext()
                .addResponseCookie(AuthCookieService.AUTH_COOKIE_NAME, CryptHelper.encrypt(jsonify(user)), properties);
    }

    public static AuthCookieDto getUserFromCookie() {
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(AuthCookieService.AUTH_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }

        String decryptedValue = CryptHelper.decrypt(cookie.getValue());
        if (Validator.isNullOrEmpty(decryptedValue)) {
            return null;
        }

        AuthCookieDto user = objectify(decryptedValue);
        if (user == null) {
            return null;
        }

        return user;
    }

    public static void deleteAuthCookie() {
        try {
            Map<String, Object> properties = new HashMap<>();
            properties.put("maxAge", 0);
            properties.put("path", "/");
            FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(AuthCookieService.AUTH_COOKIE_NAME, "", properties);
        } catch (Exception e) {
            System.err.println("Error while deleting auth cookie: " + e);
        }
    }

    private static String jsonify(User user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            LocalDateTime expirationTime = LocalDateTime.now().plusHours(4);
            AuthCookieDto dto = new AuthCookieDto(expirationTime, user.Username, user.Name, user.Telephone);
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static AuthCookieDto objectify(String user) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return objectMapper.readValue(user, AuthCookieDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
