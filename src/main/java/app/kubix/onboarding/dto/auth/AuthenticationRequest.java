package app.kubix.onboarding.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "blank") String email,
        @NotBlank(message = "blank") String password) {
}
