package com.techflow.ws.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techflow.ws.sys.entity.GroupUser;
import com.techflow.ws.sys.entity.GroupUserId;

public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserId> {

}
