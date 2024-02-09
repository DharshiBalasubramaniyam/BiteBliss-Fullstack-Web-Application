package com.seng22212project.bitebliss.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "Username is required!")
    @Size(min= 3, message = "Username must have atleast 3 characters!")
    @Size(max= 20, message = "Username can have have atmost 20 characters!")
    private String userName;

    @Email(message = "Email is not in valid format!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must have atleast 8 characters!")
    @Size(max = 20, message = "Password can have have atmost 20 characters!")
    private String password;

    private Set<String> roles;


    public SignUpRequestDto(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
