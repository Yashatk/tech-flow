package com.techflow.ws.sys.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/*
 * Usuários por grupos de acesso
 */
@Entity
@Table(name="tflow_grupo_usuario")
public class GroupUser {
    
    @EmbeddedId
    private GroupUserId id;

    public GroupUser() {
    }
    public GroupUser(GroupUserId id) {
        this.id = id;
    }
    public GroupUserId getId() {
        return id;
    }
    public void setId(GroupUserId id) {
        this.id = id;
    }    
}
