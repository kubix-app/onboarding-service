package app.kubix.onboarding.controller;

import app.kubix.onboarding.dto.auth.AuthenticationRequest;
import app.kubix.onboarding.dto.auth.AuthenticationResponse;
import app.kubix.onboarding.dto.ResponseModel;
import app.kubix.onboarding.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthenticationService service;

	@PostMapping(value = "/sign-in")
	public ResponseModel<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
		return new ResponseModel<>(service.authenticate(request));
	}

}
