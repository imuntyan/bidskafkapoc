package com.openlane.bids.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface BidProducerMetadata {
	 
		@Output("bids")
    	MessageChannel post();
}