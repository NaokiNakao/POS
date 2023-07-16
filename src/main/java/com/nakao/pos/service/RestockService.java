package com.nakao.pos.service;

import com.nakao.pos.model.Restock;
import com.nakao.pos.util.exception.RestockProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Service
@Transactional
public interface RestockService {

    List<Restock> getRestocks();

    Restock getRestockById(UUID id);

    Restock createRestock(Restock restock);

    Restock updateRestock(UUID id, Restock restock);

    void deleteRestock(UUID id);

    /**
     * Processes the inventory restock with the specified ID.
     * Updates the restocking status to "PROCESSED" and updates the product stock.
     *
     * @param id the ID of the inventory restock to process
     * @throws RestockProcessingException if unable to process the restocking
     */
    void restockProcessing(UUID id);

}
