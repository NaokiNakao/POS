package com.nakao.pos.controller;

import com.nakao.pos.model.Restock;
import com.nakao.pos.service.RestockService;
import com.nakao.pos.util.exception.RestockNotFoundException;
import com.nakao.pos.util.exception.RestockProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@RestController
@RequestMapping(path = "/api/restocks")
@RequiredArgsConstructor
public class RestockController {

    private final RestockService restockService;

    @GetMapping
    public ResponseEntity<List<Restock>> getRestocks() {
        List<Restock> restocks = restockService.getRestocks();
        return ResponseEntity.ok(restocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestock(@PathVariable UUID id) {
        try {
            Restock restock = restockService.getRestockById(id);
            return new ResponseEntity<>(restock, HttpStatus.OK);
        } catch (RestockNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Restock> createRestock(@Valid @RequestBody Restock restock) {
        Restock createdRestock = restockService.createRestock(restock);
        return new ResponseEntity<>(createdRestock, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restock> updateRestock(@PathVariable UUID id, @Valid @RequestBody Restock restock) {
        Restock updatedRestock = restockService.updateRestock(id, restock);
        return new ResponseEntity<>(updatedRestock, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestock(@PathVariable UUID id) {
        try {
            restockService.deleteRestock(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RestockNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<String> restockProcessing(@PathVariable UUID id) {
        try {
            restockService.restockProcessing(id);
            return new ResponseEntity<>("Restocking processed", HttpStatus.OK);
        }
        catch (RestockProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
