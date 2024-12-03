package org.abdellah.ebankify1.dto;


import lombok.Getter;
import lombok.Setter;
import org.abdellah.ebankify1.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Setter
@Getter
public class UserDTO {
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    private UserRole role;

}