package com.openlane.bids.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface BidStatsProcessorMetadata {
	
		@Input("bids")
    	SubscribableChannel read();
	 
		@Output("bidstats")
    	MessageChannel post();
}