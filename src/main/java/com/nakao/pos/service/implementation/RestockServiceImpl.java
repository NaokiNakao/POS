package com.nakao.pos.service.implementation;

import com.nakao.pos.dao.RestockDAO;
import com.nakao.pos.model.Restock;
import com.nakao.pos.service.RestockService;
import com.nakao.pos.util.enumeration.RestockStatus;
import com.nakao.pos.util.exception.RestockNotFoundException;
import com.nakao.pos.util.exception.RestockProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class RestockServiceImpl implements RestockService {

    private final RestockDAO dao;

    @Override
    public List<Restock> getRestocks() {
        return dao.findAll();
    }

    @Override
    public Restock getRestockById(UUID id) {
        Optional<Restock> restock = dao.findById(id);
        if (restock.isPresent()) {
            return restock.get();
        }
        else {
            throw new RestockNotFoundException("Restock not found");
        }
    }

    @Override
    public Restock createRestock(Restock restock) {
        return dao.insert(restock);
    }

    @Override
    public Restock updateRestock(UUID id, Restock restock) {
        return dao.update(id, restock);
    }

    @Override
    public void deleteRestock(UUID id) {
        if (dao.findById(id).isPresent()) {
            dao.delete(id);
        }
        else {
            throw new RestockNotFoundException("Restock not found");
        }
    }

    /**
     * Processes the inventory restock with the specified ID.
     * Updates the restocking status to "PROCESSED" and updates the product stock.
     *
     * @param id the ID of the inventory restock to process
     * @throws RestockProcessingException if unable to process the restocking
     */
    @Override
    public void restockProcessing(UUID id) {
        Restock restock = getRestockById(id);

        if (restock.getStatus().equals(RestockStatus.PENDING.getStatus())) {
            dao.updateStatus(restock.getId(), RestockStatus.PROCESSED);
            dao.updateProductStock(restock.getProduct(), restock.getProductQuantity());
        }
        else {
            throw new RestockProcessingException("Unable to process restock");
        }
    }

}
