package com.nakao.pos.service.implementation;

import com.nakao.pos.repository.RestockRepository;
import com.nakao.pos.model.Restock;
import com.nakao.pos.service.RestockService;
import com.nakao.pos.util.enumeration.RestockStatus;
import com.nakao.pos.util.exception.RestockDeletionException;
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

    private final RestockRepository repository;

    @Override
    public List<Restock> getRestocks() {
        return repository.findAll();
    }

    @Override
    public Restock getRestockById(UUID id) {
        Optional<Restock> restock = repository.findById(id);
        if (restock.isPresent()) {
            return restock.get();
        }
        else {
            throw new RestockNotFoundException("Restock not found");
        }
    }

    @Override
    public Restock createRestock(Restock restock) {
        return repository.insert(restock);
    }

    @Override
    public Restock updateRestock(UUID id, Restock restock) {
        return repository.update(id, restock);
    }

    @Override
    public void deleteRestock(UUID id) {
        Optional<Restock> restock = repository.findById(id);

        if (restock.isPresent()) {
            if (isRestockPending(restock.get().getStatus())) {
                repository.delete(id);
            }
            else {
                throw new RestockDeletionException("Unable to delete restock");
            }
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
            repository.updateStatus(restock.getId(), RestockStatus.PROCESSED);
            repository.updateProductStock(restock.getProduct(), restock.getProductQuantity());
        }
        else {
            throw new RestockProcessingException("Unable to process restock");
        }
    }

    private Boolean isRestockPending(String restockStatus) {
        return restockStatus.equals(RestockStatus.PENDING.getStatus());
    }

}
