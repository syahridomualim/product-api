package com.example.productapi.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Email(message = "email format must be valid")
    @Length(min = 2, max = 50, message = "email must contain between 2 and 50 characters")
    private String email;

    @NotNull
    @Length(min = 8, max = 15, message = "password must contain between 8 and 15 characters")
    private String password;
}
