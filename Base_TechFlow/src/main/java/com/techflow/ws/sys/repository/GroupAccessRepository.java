package com.techflow.ws.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techflow.ws.sys.entity.GroupAccess;
import com.techflow.ws.sys.entity.GroupAccessId;

public interface GroupAccessRepository extends JpaRepository<GroupAccess, GroupAccessId> {

}
