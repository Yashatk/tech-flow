package com.techflow.ws.sys.entity;

public enum HistoryTypeEnum {
    SISTEMA(1),
    USUARIO(2),
    SUPORTE(3),
    RELATORIO(4);

    private final int id;
    HistoryTypeEnum(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
}
