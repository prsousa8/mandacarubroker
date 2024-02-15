package com.mandacarubroker.domain.stock;

import static org.junit.Assert.fail;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Table(name ="stock")
@Entity(name="stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    @NotNull
    private String id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "price")
    private double price;


    public Stock(RequestStockDTO requestStockDTO){
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = changePrice(requestStockDTO.price(), true);
    }

    public double changePrice(double amount, boolean increase) {
        if (increase) {
            return this.price + amount; // Aumenta o preço adicionando o valor fornecido
        } else {
            if (amount > this.price) {
                return 0; // Impede que o preço fique negativo durante uma diminuição
            } else {
                return this.price - amount; // Diminui o preço subtraindo o valor fornecido
            }
        }
    }

}