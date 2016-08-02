package com.openlane.bids.dto;

import java.math.BigDecimal;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class BidStatsDto {

    private BigDecimal vehicleId;

    private BigDecimal count;

    private BigDecimal amount;

    private DateTime createTime;

}
