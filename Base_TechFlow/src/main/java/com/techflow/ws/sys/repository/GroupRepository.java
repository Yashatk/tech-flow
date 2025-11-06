package com.techflow.ws.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techflow.ws.sys.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    boolean existsByGroupName(String groupName);

    Group findByGroupName(String string);

}
