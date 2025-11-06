package com.techflow.ws.sys.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/*
 * Acessos por grupos de acesso
 */
@Entity
@Table(name="tflow_grupo_acesso")
public class GroupAccess {

    @EmbeddedId
    private GroupAccessId id;

    public GroupAccess() {
    }
    public GroupAccess(GroupAccessId id) {
        this.id = id;
    }
    public GroupAccessId getId() {
        return id;
    }
    public void setId(GroupAccessId id) {
        this.id = id;
    }
  
}
