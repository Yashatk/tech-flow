package com.techflow.ws.sys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techflow.ws.sys.domain.EncryptUtils;
import com.techflow.ws.sys.domain.ServiceResponse;
import com.techflow.ws.sys.entity.Group;
import com.techflow.ws.sys.entity.GroupAccess;
import com.techflow.ws.sys.entity.GroupAccessId;
import com.techflow.ws.sys.entity.GroupUser;
import com.techflow.ws.sys.entity.GroupUserId;
import com.techflow.ws.sys.entity.SysAccess;
import com.techflow.ws.sys.entity.SysAccessEnum;
import com.techflow.ws.sys.entity.SysUser;
import com.techflow.ws.sys.repository.GroupAccessRepository;
import com.techflow.ws.sys.repository.GroupRepository;
import com.techflow.ws.sys.repository.GroupUserRepository;
import com.techflow.ws.sys.repository.SysAccessRepository;
import com.techflow.ws.sys.repository.SysUserRepository;

@Service
public class SysUserService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private GroupAccessRepository groupAccessRepository;

    @Autowired
    private SysAccessRepository sysAccessRepository;

    private static final Logger log = LogManager.getLogger(SysUserService.class);

    //@PostConstruct
    public void init() {
        // Cria usuário admin padrão se não existir
        if(!sysUserRepository.existsById(1)) {
            sysUserRepository.save(new SysUser("admin", EncryptUtils.encrypt("admin"), "Administrador", "admin@example.com"));
        }
        // Cria grupos padrão se não existirem
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("Administrador", "Administradores do sistema"));
        groups.add(new Group("Analistas", "Analistas de suporte"));
        groups.add(new Group("Clientes", "Clientes"));
        for(Group group : groups) {
            if(!groupRepository.existsByGroupName(group.getGroupName())) {
                groupRepository.save(group);
            }
        }
        // Grupo Administrador e Usuário Administrador
        Group adminGroup = groupRepository.findByGroupName("Administrador");
        SysUser adminUser = sysUserRepository.findByUsername("admin");

        // Cria acessos padrão se não existirem
        for(SysAccessEnum item : SysAccessEnum.values()) {
            SysAccess access = sysAccessRepository.findById(item.name()).orElse(null);
            if(access == null) {
                access = sysAccessRepository.save(new SysAccess(item.name(),item.getDescricao()));                
            }
            if(access != null) {
                // Vincula acesso ao grupo Administrador
                groupAccessRepository.save(new GroupAccess(new GroupAccessId(adminGroup, access)));
            }
        }
        
        // Vincula usuário admin ao grupo Administrador
        if(!groupUserRepository.existsById(new GroupUserId(adminGroup,adminUser))) {
            groupUserRepository.save(new GroupUser(new GroupUserId(adminGroup, adminUser)));
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );
    }

    public ServiceResponse<List<SysUser>> getUser(Integer uid) {
        ServiceResponse<List<SysUser>> response = new ServiceResponse<>();
        try {
            List<SysUser> users;
            if(uid == null || uid < 0)
                users = sysUserRepository.findAll();
            else {
                users = sysUserRepository.findById(uid)
                        .map(List::of)
                        .orElse(new ArrayList<>());
            }
            response.setObj(users);
            response.setOK();
        }
        catch (Exception e) {
            response.setMessage("Erro ao buscar usuário: " + e.getMessage());
        }
        return response;
    }

    public ServiceResponse<SysUser> createUser(SysUser user) {
        ServiceResponse<SysUser> response = new ServiceResponse<>();
        try {
            // Verifica se o username já existe
            if(sysUserRepository.existsByUsername(user.getUsername())) {
                response.setMessage("Username já existe.");
                return response;
            }
            // Criptografa a senha
            user.setPassword(EncryptUtils.encrypt(user.getPassword()));
            SysUser savedUser = sysUserRepository.save(user);
            response.setObj(savedUser);
            response.setOK();
        }
        catch (Exception e) {
            response.setMessage("Erro ao criar usuário: " + e.getMessage());
        }
        return response;
    }

    public ServiceResponse<String> deleteUser(Integer uid) {
        ServiceResponse<String> response = new ServiceResponse<>();
        try {
            if(!sysUserRepository.existsById(uid)) {
                response.setMessage("Usuário não encontrado.");
                return response;
            }
            sysUserRepository.deleteById(uid);
            response.setObj("Usuário deletado com sucesso.");
            response.setOK();
        }
        catch (Exception e) {
            response.setMessage("Erro ao deletar usuário: " + e.getMessage());
        }
        return response;
    }

    public ServiceResponse<SysUser> updateUser(Integer uid, SysUser user) {
        ServiceResponse<SysUser> response = new ServiceResponse<>();
        try {
            SysUser existingUser = sysUserRepository.findById(uid).orElse(null);
            if(existingUser == null) {
                response.setMessage("Usuário não encontrado.");
                return response;
            }
            // Atualiza os campos
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(EncryptUtils.encrypt(user.getPassword()));
            SysUser updatedUser = sysUserRepository.save(existingUser);
            response.setObj(updatedUser);
            response.setOK();
        }
        catch (Exception e) {
            response.setMessage("Erro ao atualizar usuário: " + e.getMessage());
        }
        return response;
    }
}
