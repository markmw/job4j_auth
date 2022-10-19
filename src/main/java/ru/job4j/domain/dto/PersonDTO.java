package ru.job4j.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonDTO {
    @NotBlank(message = "Username cannot be empty!")
    @Size(min = 3, max = 16, message = "The name should not be shorter than {min} and longer than {max} characters!")
    private String username;

    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 6, message = "The password cannot be less than {min} characters!")
    private String password;
}
