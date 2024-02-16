package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import com.mandacarubroker.service.exceptions.CampoNuloException;
import com.mandacarubroker.service.exceptions.CampoVazioException;
import com.mandacarubroker.service.exceptions.ObjectNotFoundException;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StockService {

    @Autowired
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(String id) {
        return stockRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Stock createStock(RequestStockDTO data) {
        try {
            Stock novaAcao = new Stock(data);
            validateRequestStockDTO(data);
            return stockRepository.save(novaAcao);
        }catch (ConstraintViolationException ex){
            throw new CampoVazioException("Os campos symbol e companyName não podem estar vazios ou nulos.");
        }
    }

    public Optional<Stock> updateStock(String id, Stock updatedStock) {
        Optional<Stock> existingStock = stockRepository.findById(id);

        if (existingStock.isEmpty()) {
            throw new ObjectNotFoundException("Registro não encontrado para o ID: " + id);
        } else if (updatedStock.getSymbol() == null || updatedStock.getCompanyName() == null) {
            throw new CampoNuloException("Os campos symbol e companyName não podem ser nulos.");
        } else if (updatedStock.getSymbol().isBlank() || updatedStock.getCompanyName().isBlank()) {
            throw new CampoVazioException("Os campos symbol e companyName não podem estar vazios.");
        }
        else{
            return existingStock
                    .map(stock -> {
                        stock.setSymbol(updatedStock.getSymbol());
                        stock.setCompanyName(updatedStock.getCompanyName());
                        double newPrice = stock.changePrice(updatedStock.getPrice(), true);
                        stock.setPrice(newPrice);

                        return stockRepository.save(stock);
                    });
        }
    }

    public void deleteStock(String id) {
        if (stockRepository.findById(id).isEmpty()) {
            throw new ObjectNotFoundException("Registro não encontrado para o ID: " + id);
        }else {
            stockRepository.deleteById(id);
        }
    }

    public static void validateRequestStockDTO(RequestStockDTO data) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RequestStockDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RequestStockDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }

            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }
    }

    public void validateAndCreateStock(RequestStockDTO data) {
        validateRequestStockDTO(data);

        Stock novaAcao = new Stock(data);
        stockRepository.save(novaAcao);
    }
}