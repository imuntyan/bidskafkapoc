package com.openlane.bids.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import com.openlane.bids.dto.BidStats;
import com.openlane.bids.dto.Bid;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;

@Log
@EnableBinding(BidStatsProcessorMetadata.class)
public class BidStatsProcessorProcessor {

	@StreamListener("BidMessage")
  	@SendTo("bidstats")
  	public Bid handle(BidStats bidStats) {
    	return new Bid();
  	}
}