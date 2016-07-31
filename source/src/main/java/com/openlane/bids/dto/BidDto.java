package com.openlane.bids.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BidDto {

    private BigDecimal amount;

    private DateTime createTime;

}
