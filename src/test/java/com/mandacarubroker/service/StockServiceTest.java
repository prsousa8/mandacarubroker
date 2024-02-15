package com.mandacarubroker.service;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import com.mandacarubroker.domain.stock.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {
    @InjectMocks
    StockService stockService;
    @Mock
    StockRepository stockRepository;
    private RequestStockDTO requestStockDTO;
    private Stock stock;
    private final String ID = "TestID";
    private final String SYMBOL = "CB1";
    private final String COMPANY = "Cucumber Bank";
    private final double PRICE = 12.89;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockService(stockRepository);
        stock = new Stock(ID,SYMBOL,COMPANY,PRICE);
        Stock stock1 = new Stock("TesteID1","CB1","Cucumber Bank",12.98);
        Stock stock2 = new Stock("TesteID2","CB2","Cucumber Bank",45.89);
        List<Stock> listaStocks = new ArrayList<Stock>();
        listaStocks.add(stock1);
        listaStocks.add(stock2);
    }

    @Test
    void getAllStocks() {
        // Mockando o comportamento do stockRepository
        Stock stock1 = new Stock("TesteID1","CB1","Cucumber Bank",12.98);
        Stock stock2 = new Stock("TesteID2","CB2","Cucumber Bank",45.89);
        List<Stock> mockStockList = Arrays.asList(stock1, stock2);

        when(stockRepository.findAll()).thenReturn(mockStockList);

        // Chamando o método do serviço
        List<Stock> result = stockService.getAllStocks();

        // Verificando o resultado
        assertEquals(2, result.size());
        assertEquals("CB1", result.get(0).getSymbol());
        assertEquals("CB2", result.get(1).getSymbol());


    }

    @Test
    void getStockById() {
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.ofNullable(stock));

        Stock response = stockService.getStockById(ID);

        assertEquals(stock,response);

        verify(stockRepository).findById(stock.getId());
        verifyNoMoreInteractions(stockRepository);
    }

    @Test
    void createStock() {
        // Dados de entrada para o novo Stock
        requestStockDTO= new RequestStockDTO("CB2", "New Company", 25.0);

        // Mockando o comportamento do stockRepository ao salvar
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> {
            Stock createdStock = invocation.getArgument(0);
            createdStock.setId("generatedID");  // Simula a geração de um ID pelo banco
            return createdStock;
        });

        // Chamando o método do serviço para criar uma nova Stock
        Stock createdStock = stockService.createStock(requestStockDTO);

        // Verificando se o método save do stockRepository foi chamado corretamente
        verify(stockRepository, times(1)).save(any(Stock.class));

        // Verificando se o objeto criado pelo serviço é o esperado
        assertNotNull(createdStock);
        assertEquals("generatedID", createdStock.getId()); // Certifique-se de ajustar conforme a lógica real
        assertEquals("CB2", createdStock.getSymbol());
        assertEquals("New Company", createdStock.getCompanyName());
        assertEquals(-25.0, createdStock.getPrice());


    }

    @Test
    void updateStock() {
        // Dado um ID existente e uma instância de Stock atualizada
        String existingStockId = "existingStockId";
        Stock updatedStock = new Stock(existingStockId, "UpdatedSymbol", "UpdatedCompany", 30.0);

        // Mockando o comportamento do stockRepository ao chamar o findById e save
        when(stockRepository.findById(existingStockId)).thenReturn(Optional.of(new Stock(existingStockId, "OldSymbol", "OldCompany", 20.0)));
        when(stockRepository.save(any(Stock.class))).thenReturn(updatedStock);

        // Chamando o método updateStock
        Optional<Stock> result = stockService.updateStock(existingStockId, updatedStock);

        // Verificando se o método findById do stockRepository foi chamado corretamente
        verify(stockRepository, times(1)).findById(existingStockId);

        // Verificando se o método save do stockRepository foi chamado corretamente
        verify(stockRepository, times(1)).save(any(Stock.class));

        // Verificando se o resultado não é vazio e se os dados foram atualizados corretamente
        assertTrue(result.isPresent());
        assertEquals(existingStockId, result.get().getId());
        assertEquals("UpdatedSymbol", result.get().getSymbol());
        assertEquals("UpdatedCompany", result.get().getCompanyName());
        assertEquals(30.0, result.get().getPrice(), 0.001);
    }

    @Test
    void deleteStock() {
        // Dado um ID existente
        String existingStockId = "existingStockId";

        // Chamando o método deleteStock
        stockService.deleteStock(existingStockId);

        // Verificando se o método deleteById do stockRepository foi chamado corretamente
        verify(stockRepository, times(1)).deleteById(existingStockId);
    }
}