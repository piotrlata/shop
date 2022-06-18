package com.example.shop.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends AuditableDto {
    private Long id;
    @NotBlank
    private String name;
    @NotEmpty
    private String description;
    private Integer revisionNumber;
}
