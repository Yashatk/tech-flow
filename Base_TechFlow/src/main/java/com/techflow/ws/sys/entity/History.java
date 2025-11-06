package com.techflow.ws.sys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tflow_historico")
public class History extends CommonTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idhistorico", length=9, nullable=false)
    private Integer id;

    @Column(name="idhistorico_tipo", length=30, nullable=true)
    private String historyTypeId;

    @Column(name="descricao", columnDefinition = "TEXT", nullable=true)
    private String description;

    public History() {
    }
    public History(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}
