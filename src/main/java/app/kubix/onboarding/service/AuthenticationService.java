package app.kubix.onboarding.service;

import app.kubix.onboarding.dto.auth.AuthenticationRequest;
import app.kubix.onboarding.dto.auth.AuthenticationResponse;

public interface AuthenticationService {

	AuthenticationResponse authenticate(AuthenticationRequest request);
}
