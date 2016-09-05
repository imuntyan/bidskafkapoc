package com.openlane.bids.stream;

import com.openlane.bids.dto.BidDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import lombok.extern.java.Log;

@Log
@Component
@EnableBinding(BidProducerMetadata.class)
public class BidProducerSource {

        @Autowired @Qualifier("bids")
        private MessageChannel post;

        @Autowired
        ObjectMapper mapper;

        public void send(BidDto bid) {
                try {
                        Message<?> message = MessageBuilder.withPayload(
                                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bid)
                                )
                                .setHeader("contentType", "application/json")
                                .build();
                        post.send(message);
                        log.info("sent message " + bid);
                } catch (Exception ex) {
                        log.log(Level.SEVERE, "Error trying to send a message to a queue: ", ex);
                }        
        }

}
