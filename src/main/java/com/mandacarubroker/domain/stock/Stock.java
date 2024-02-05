package com.mandacarubroker.domain.stock;

import static org.junit.Assert.fail;

import jakarta.persistence.*;
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
    @NotEmpty
    private String id;

    @Column(name = "symbol")
    //@NotNull
    //@NotEmpty
   // @Size(min = 3, max = 3)
    private String symbol;

    @Column(name = "company_name")
   // @NotNull
    //@NotEmpty
    private String companyName;

    @Column(name = "price")
    //@NotNull
   // @NotEmpty
    private double price;


    public Stock(RequestStockDTO requestStockDTO){
        this.symbol = requestStockDTO.symbol();
        this.companyName = requestStockDTO.companyName();
        this.price = changePrice(requestStockDTO.price(), true);
    }

    public double changePrice(double amount, boolean increase) {
        if (increase) {
            if (amount < this.price) {
                return increasePrice(amount);
            } else {
                return decreasePrice(amount);
            }
        } else {
            if (amount > this.price) {
                return increasePrice(amount);
            } else {
                return this.decreasePrice(amount);
            }
        }
    }

    public double increasePrice(double amount) {
        return this.price + amount;
    }

    public double decreasePrice(double amount) {
        return this.price - amount;
    }

}