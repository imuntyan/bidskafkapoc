package com.openlane.bids.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBCollection;
import com.openlane.bids.dto.BidDto;
import com.openlane.bids.dto.BidStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

	@Autowired
	private MongoTemplate mongoTemplate;


	@StreamListener("bids")
  	@SendTo("bidstats")
  	public Message<?> handle(BidDto bid) {
    	BidStatsDto bidStatsDto= new BidStatsDto();

		BigDecimal vehicleId = bid.getVehicleId();


		DBCollection dbCollection = mongoTemplate.getCollection("bids");
		Query query = new Query(Criteria.where("vehicleId").is(bid.getVehicleId()));
		Update update = new Update().inc("count", 1);
		BidStatsDto bs = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true).upsert(true), BidStatsDto.class);
		System.out.println(bs.getCount());

		bidStatsDto.setCount(bs.getCount());
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