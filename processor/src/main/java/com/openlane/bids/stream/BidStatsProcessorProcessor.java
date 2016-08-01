package com.openlane.bids.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openlane.bids.dto.BidDto;
import com.openlane.bids.dto.BidStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

import lombok.extern.java.Log;

import java.math.BigDecimal;

@Log
@EnableBinding(BidStatsProcessorMetadata.class)
public class BidStatsProcessorProcessor {

	@Autowired
	ObjectMapper mapper;

	@StreamListener("bids")
  	@SendTo("bidstats")
  	public Message<?> handle(BidDto bid) {
    	BidStatsDto bidStatsDto= new BidStatsDto();

		bidStatsDto.setCount(BigDecimal.ONE);
		bidStatsDto.setAmount(bid.getAmount());
		bidStatsDto.setCreateTime(bid.getCreateTime());

		try {
			return MessageBuilder.withPayload(
					mapper.writeValueAsString(bidStatsDto)
			)
					.setHeader("contentType", "application/json")
					.build();
		} catch (Exception e) { throw new RuntimeException(e);}

	}
}