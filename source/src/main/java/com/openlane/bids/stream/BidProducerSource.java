package com.openlane.bids.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import com.openlane.bids.dto.Bids;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import lombok.extern.java.Log;

@Log
@Component
public class BidProducerSource {

        @Autowired @Qualifier("bidsin")
        private MessageChannel post;

        @Autowired
        ObjectMapper mapper;

        public void send(Bids bids) {
                try {
                        Message<?> message = MessageBuilder.withPayload(
                                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bids)
                                )
                                .setHeader("content-type", "application/json")
                                .build();
                        post.send(message);
                } catch (Exception ex) {
                        log.log(Level.SEVERE, "Error trying to send a message to a queue: ", ex);
                }        
        }

}
