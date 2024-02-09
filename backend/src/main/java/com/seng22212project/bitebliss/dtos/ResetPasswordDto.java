package com.seng22212project.bitebliss.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordDto {

    @NotNull(message = "Email is required!")
    @Email(message = "Email is not in valid format!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must have atleast 8 characters!")
    @Size(max= 20, message = "Password can have have atmost 20 characters!")
    private String newPassword;

}
