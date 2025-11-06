package com.techflow.ws.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techflow.ws.sys.domain.EncryptUtils;



@RestController
public class EncryptController {

    @GetMapping("api/encrypt")
    public String encrypt(@RequestParam String password, @RequestParam(required=false) String iv, @RequestParam(required=false) String key) throws Exception {
        if(iv == null || key == null) {
            return EncryptUtils.encrypt(password);
        }
        return EncryptUtils.encrypt(password, iv, key);
    }

    @GetMapping("api/decrypt")
    public String decrypt(@RequestParam String password, @RequestParam(required=false) String iv, @RequestParam(required=false) String key) throws Exception {
        if(iv == null || key == null) {
            return EncryptUtils.decrypt(password);
        }
        return EncryptUtils.decrypt(password, iv, key);
    }
    
}
