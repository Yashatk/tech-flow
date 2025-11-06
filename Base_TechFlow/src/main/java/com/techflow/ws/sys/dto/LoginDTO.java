package com.techflow.ws.sys.dto;

import java.util.List;

public class LoginDTO {

    private Integer userId;
    private String username;
    private String name;
    private String email;
    private String token;

    private List<String> accesses;

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<String> accesses) {
        this.accesses = accesses;
    }
}
