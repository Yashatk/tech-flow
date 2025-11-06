package com.techflow.ws.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techflow.ws.sys.entity.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {

}
