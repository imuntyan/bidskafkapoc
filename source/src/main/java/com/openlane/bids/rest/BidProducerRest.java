package com.openlane.bids.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.openlane.bids.stream.BidProducerSource;
import com.openlane.bids.dto.Bids;

@RestController
@RequestMapping("/bidProducers")
public class BidProducerRest {

        @Autowired
        private BidProducerSource source;


        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity<?> process(@RequestBody Bids bids ) {
                source.send(bids);

                HttpHeaders httpHeaders = new HttpHeaders();

                /* - Uncomment to add some additional headers.
                httpHeaders.setLocation(ServletUriComponentsBuilder
                                .fromCurrentRequest().path("/{id}")
                                .buildAndExpand(bids.getId()).toUri());
                */
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
        }
}
