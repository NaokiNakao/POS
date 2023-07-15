package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "inventory_restock")
public class Restock {

    @Id
    private UUID id;
    private LocalDate deliveryDate;
    private String product;
    private Integer productQuantity;
    private String supplier;
    private String status;

}
