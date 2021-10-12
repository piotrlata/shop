package com.example.shop.domain.dto;

import com.example.shop.validator.PasswordValid;
import com.example.shop.validator.group.CreateUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@PasswordValid(groups = CreateUser.class)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AuditableDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank(groups = CreateUser.class)
    private String password;
    @NotBlank(groups = CreateUser.class)
    private String confirmedPassword;
    private Integer revisionNumber;
}
