package com.techflow.ws.sys.controller;

import org.springframework.web.bind.annotation.RestController;

import com.techflow.ws.sys.domain.ServiceResponse;
import com.techflow.ws.sys.dto.LoginDTO;
import com.techflow.ws.sys.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("api/login")
    public ResponseEntity<ServiceResponse<LoginDTO>> login(@RequestParam String username, @RequestParam String password) {
        ServiceResponse<LoginDTO> resp = loginService.login(username, password);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
    
    @GetMapping("api/logout")
    public ResponseEntity<ServiceResponse<String>> logout(@RequestParam Integer uid) {
        ServiceResponse<String> resp = loginService.logout(uid);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
    
}
