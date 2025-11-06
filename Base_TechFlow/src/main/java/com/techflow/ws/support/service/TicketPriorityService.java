package com.techflow.ws.support.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techflow.ws.support.entity.TicketPriority;
import com.techflow.ws.support.entity.TicketPriorityEnum;
import com.techflow.ws.support.repository.TicketPriorityRepository;

import jakarta.annotation.PostConstruct;

@Service
public class TicketPriorityService {

    @Autowired
    TicketPriorityRepository ticketPriorityRepository;

    @PostConstruct
    public void init() {
        for(TicketPriorityEnum item : TicketPriorityEnum.values()) {
            if(!ticketPriorityRepository.existsById(item.getId())) {
                var entity = new TicketPriority();
                entity.setId(item.getId());
                entity.setPriority(item.name());
                ticketPriorityRepository.save(entity);
            }
        }
    }
}
