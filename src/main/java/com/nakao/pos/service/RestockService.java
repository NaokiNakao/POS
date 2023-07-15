package com.nakao.pos.service;

import com.nakao.pos.model.Restock;
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

    void restockProcessing(UUID id);

}
