package com.forj.hub.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record HubRequestDto(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Address cannot be blank")
        String address
) {

}
