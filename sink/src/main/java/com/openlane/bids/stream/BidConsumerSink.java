package com.openlane.bids.stream;

import com.openlane.bids.dto.BidStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import lombok.extern.java.Log;

@Log
@EnableBinding(BidConsumerMetadata.class)
public class BidConsumerSink {

    @Autowired
    ObjectMapper mapper;


    @StreamListener("bidstats")
    public void sink(BidStatsDto bidStats) {
        
        try {
            log.log(Level.INFO, this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bidStats));
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Exception thrown and could not map.", ex);
        }
    }
}
