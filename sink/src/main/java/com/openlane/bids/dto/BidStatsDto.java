package com.openlane.bids.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BidStatsDto {

    private BigDecimal count;

    private BigDecimal amount;

    private DateTime createTime;

}
