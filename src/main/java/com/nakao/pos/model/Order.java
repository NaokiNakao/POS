package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "item_order")
public class Order {

    @Id
    private UUID id;
    private LocalDate date;
    private BigDecimal net;
    private BigDecimal tax;
    private BigDecimal total;
    private String paymentMethod;
    private String status;
    private String customer;
    private String employee;

}
