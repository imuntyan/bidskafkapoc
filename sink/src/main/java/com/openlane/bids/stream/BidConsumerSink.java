package com.openlane.bids.stream;

import com.openlane.bids.dto.BidStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Log
@EnableBinding(BidConsumerMetadata.class)
@Component
public class BidConsumerSink {

    @Autowired
    ObjectMapper mapper;

    @Inject
    SimpMessageSendingOperations messagingTemplate;

    @StreamListener("bidstats")
    public void sink(BidStatsDto bidStats) {
        
        try {
            log.log(Level.INFO, this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bidStats));
            sendToClients(bidStats);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Exception thrown and could not map.", ex);
        }
    }

    public void sendToClients(BidStatsDto dto) {
        String json = "";
        try {
            json = mapper.writeValueAsString(dto);
        } catch (Exception ex) {ex.printStackTrace();}
        log.log(Level.INFO, "Sending to clients: " + json);
        messagingTemplate.convertAndSend("/topic/tracking", json);
    }

}
