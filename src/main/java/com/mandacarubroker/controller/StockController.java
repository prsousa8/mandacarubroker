package com.mandacarubroker.controller;


import com.mandacarubroker.domain.stock.*;
import com.mandacarubroker.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable String id) {
        return stockService.getStockById(id);
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody RequestStockDTO data) {
        Stock createdStock = stockService.createStock(data);
        return ResponseEntity.ok(createdStock);
    }

    @PutMapping("/{id}")
    public Optional<Stock> updateStock(@PathVariable String id, @RequestBody Stock updatedStock) {
        return stockService.updateStock(id, updatedStock);
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable String id) {
        stockService.deleteStock(id);
    }

}