package com.techflow.ws.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techflow.ws.sys.domain.ServiceResponse;
import com.techflow.ws.sys.entity.SysUser;
import com.techflow.ws.sys.service.SysUserService;


// Usuários - CRUD - CONTROLLER
@RestController
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    // CRUD - CREATE
    @PostMapping("api/user")
    public ResponseEntity<ServiceResponse<SysUser>> createUser(@RequestBody SysUser user) {
        ServiceResponse<SysUser> response = sysUserService.createUser(user);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    // CRUD - READ
    @GetMapping("api/user")
    public ResponseEntity<ServiceResponse<List<SysUser>>> getUser(@RequestParam(required=false) Integer uid) {        
        ServiceResponse<List<SysUser>> response = sysUserService.getUser(uid);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // CRUD - UPDATE
    @PatchMapping("api/user/{uid}")
    public ResponseEntity<ServiceResponse<SysUser>> updateUser(@PathVariable Integer uid, @RequestBody SysUser user) {
        ServiceResponse<SysUser> response = sysUserService.updateUser(uid, user);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // CRUD - DELETE
    @DeleteMapping("api/user/{uid}")
    public ResponseEntity<ServiceResponse<String>> deleteUser(@PathVariable Integer uid) {
        ServiceResponse<String> response = sysUserService.deleteUser(uid);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    
}
