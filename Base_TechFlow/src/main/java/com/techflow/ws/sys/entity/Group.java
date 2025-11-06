package com.techflow.ws.sys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * Grupos de Usuários do Sistema
 */
@Entity
@Table(name="tflow_grupo")
public class Group extends CommonTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idgrupo", length=4, nullable=false)
    private Integer id;

    @Column(name="grupo", length=100, nullable=false)
    private String groupName;

    @Column(name="descricao", length=255, nullable=true)
    private String description;

    public Group() {
    }
    
    public Group(Integer id) {
        this.id = id;
    }   

    public Group(String groupName, String description) {
        this.groupName = groupName;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
