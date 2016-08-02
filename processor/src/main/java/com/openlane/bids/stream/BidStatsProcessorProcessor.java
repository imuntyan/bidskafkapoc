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
import java.util.HashMap;
import java.util.Map;

@Log
@EnableBinding(BidStatsProcessorMetadata.class)
public class BidStatsProcessorProcessor {

	@Autowired
	ObjectMapper mapper;

	Map<Long, Integer> counts = new HashMap<>();
	private BigDecimal increment(BigDecimal vehicleId)
	{
		Long vId = vehicleId.longValue();
		Integer count = counts.get(vId);
		if (count == null) count = 0;

		count++;
		counts.put(vId, count);

		return new BigDecimal(count);
	}

	@StreamListener("bids")
  	@SendTo("bidstats")
  	public Message<?> handle(BidDto bid) {
    	BidStatsDto bidStatsDto= new BidStatsDto();

		BigDecimal vehicleId = bid.getVehicleId();

		bidStatsDto.setCount(increment(vehicleId));
		bidStatsDto.setAmount(bid.getAmount());
		bidStatsDto.setCreateTime(bid.getCreateTime());
		bidStatsDto.setVehicleId(vehicleId);

		try {
			return MessageBuilder.withPayload(
					mapper.writeValueAsString(bidStatsDto)
			)
					.setHeader("contentType", "application/json")
					.build();
		} catch (Exception e) { throw new RuntimeException(e);}

	}
}