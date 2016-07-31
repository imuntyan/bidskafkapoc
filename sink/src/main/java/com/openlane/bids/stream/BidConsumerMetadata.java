package com.openlane.bids.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface BidConsumerMetadata {
	
		@Input("bidstats")
    	SubscribableChannel read();
	 
}