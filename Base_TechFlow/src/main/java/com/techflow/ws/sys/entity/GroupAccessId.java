package com.techflow.ws.sys.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class GroupAccessId {
    @ManyToOne
    @JoinColumn(name="idgrupo", nullable=false, foreignKey = @ForeignKey(name = "fktflow_grupo_acesso_grupo"))
    private Group group;

    @ManyToOne
    @JoinColumn(name="idacesso", nullable=false, foreignKey = @ForeignKey(name = "fktflow_grupo_acesso_acesso"))
    private SysAccess access;

    public GroupAccessId() {
    }
    public GroupAccessId(Group group, SysAccess access) {
        this.group = group;
        this.access = access;
    }
    public Group getGroup() {
        return group;
    }
    public SysAccess getAccess() {
        return access;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
    public void setAccess(SysAccess access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupAccessId that = (GroupAccessId) o;
        return java.util.Objects.equals(group, that.group) &&
               java.util.Objects.equals(access, that.access);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(group, access);
    }
}
