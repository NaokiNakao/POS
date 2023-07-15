package com.nakao.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosApplication.class, args);
    }

    /*@Bean
    CommandLineRunner runner(InventoryRestockDAO inventoryRestockDAO) {
        return args -> {
            InventoryRestock inventoryRestock = InventoryRestock.builder()
                    .deliveryDate(LocalDate.now())
                    .product("PRO000001")
                    .productQuantity(100)
                    .supplier("SUP000001")
                    .status("IN_PROGRESS")
                    .build();

            InventoryRestock saved = inventoryRestockDAO.insert(inventoryRestock);

            System.out.println(inventoryRestockDAO.findById(saved.getId()));
        };
    }*/

}
