package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "product")
public class Product {

    @Id
    private String id;
    private String name;
    private String category;
    private Integer stock;
    private Integer minStock;
    private BigDecimal acquisitionCost;
    private BigDecimal sellingPrice;

    public static final String ID_PATTERN = "PRO######";

}
