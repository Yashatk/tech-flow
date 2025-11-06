package com.techflow.ws.support.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techflow.ws.support.entity.TicketStatus;
import com.techflow.ws.support.entity.TicketStatusEnum;
import com.techflow.ws.support.repository.TicketStatusRepository;

@Service
public class TicketStatusService {

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    //@PostConstruct
    public void init() {

        for (TicketStatusEnum status : TicketStatusEnum.values()) {
            if (!ticketStatusRepository.existsById(status.getId())) {
                TicketStatus ticketStatus = new TicketStatus();
                ticketStatus.setId(status.getId());
                ticketStatus.setStatus(status.getDescription());
                ticketStatusRepository.save(ticketStatus);
            }
        }
    }
}
