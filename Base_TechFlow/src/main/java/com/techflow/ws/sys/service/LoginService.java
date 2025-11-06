package com.techflow.ws.sys.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import com.techflow.ws.config.JwtAuthFilter;
import com.techflow.ws.sys.domain.EncryptUtils;
import com.techflow.ws.sys.domain.ServiceResponse;
import com.techflow.ws.sys.dto.LoginDTO;
import com.techflow.ws.sys.entity.SysUser;
import com.techflow.ws.sys.repository.HistoryRepository;
import com.techflow.ws.sys.repository.SysUserRepository;

@Service
public class LoginService {

    // Use a strong secret key in production and store it securely!
    private static final long JWT_EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24 hours

    @Autowired
    SysUserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

    public static final Logger log = LogManager.getLogger(LoginService.class);

    public ServiceResponse<LoginDTO> login(String username, String password) {
        ServiceResponse<LoginDTO> resp = new ServiceResponse<>();
        try {
            SysUser user = userRepository.findByUsername(username.trim().toLowerCase());
            String descryptedPassword = EncryptUtils.decrypt(user.getPassword());
            if(user != null && EncryptUtils.decrypt(user.getPassword()).equals(password)) {
                LoginDTO dto = new LoginDTO();
                dto.setUserId(user.getId());
                dto.setUsername(user.getUsername());
                dto.setName(user.getName());
                dto.setEmail(user.getEmail());

                // JWT Token generation
                String token = Jwts.builder()
                        .setSubject(user.getUsername())
                        .claim("userId", user.getId())
                        .claim("email", user.getEmail())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                        .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(JwtAuthFilter.JWT_SECRET.getBytes()), SignatureAlgorithm.HS256)
                        .compact();
                dto.setToken(token);

                resp.setObj(dto);
                resp.setOK();
            } else {
                resp.setMessage("Usuário ou senha inválidos.");
            }
        } catch(Exception e) {
            log.error("Erro ao autenticar usuário.", e);
            resp.setMessage("Erro ao autenticar usuário: " + e.getMessage());
        }
        return resp;
    }

    public ServiceResponse<String> logout(Integer uid) {
        ServiceResponse<String> resp = new ServiceResponse<>();
        try {
            resp.setMessage("Usuário desabilitado.");
            resp.setOK();
        }
        catch(Exception e) {
            log.error("Erro ao fazer logout do usuário.", e);
            resp.setMessage("Erro ao fazer logout do usuário: " + e.getMessage());
        }
        return resp;
    }


}
