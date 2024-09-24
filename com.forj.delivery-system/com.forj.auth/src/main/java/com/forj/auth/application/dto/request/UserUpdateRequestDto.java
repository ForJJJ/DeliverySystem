package com.forj.auth.application.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDto(

        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-z0-9]+$")
        String username,
        @Size(min = 8, max = 15)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).+$")
        String password

) {

}
