package com.openlane.bids.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Igor.Muntyan on 8/25/2016.
 */

public interface BidStatsRepository<T, ID extends Serializable>
        //extends MongoRepository<T, ID>
{
}

