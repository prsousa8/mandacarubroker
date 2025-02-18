package com.mandacarubroker.domain.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RequestStockDTO(
        @Pattern(regexp = "[A-Za-z]{2}[0-9]{1}", message = "Symbol must be 3 letters followed by 1 number")
        @NotBlank(message = "Symbol cannot be null")
        String symbol,
        @NotBlank(message = "Company name cannot be blank")
        @NotNull(message = "Company name cannot be null")
        String companyName,
        @NotNull(message = "Price cannot be null")
        double price
) {
        
}
