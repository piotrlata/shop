package com.example.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends AuditableDto {
    private Long id;
    @NotNull
    private String name;
    @Min(value = 1, message = "price cannot be less than 1")
    private Double price;
    @NotNull
    private String description;
    @Min(value = 1, message = "quantity cannot be less than 1")
    private Integer quantity;
    private String filePath;
    private Integer revisionNumber;
}