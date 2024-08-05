package app.kubix.onboarding.exception;

import app.kubix.common.exception.codes.ExceptionKeyAndMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OnboardingExceptionCodes implements ExceptionKeyAndMessage {

	INVALID_EMAIL_EXCEPTION("1000", "Invalid email address"),
    INVALID_CREDENTIALS("1001", "Invalid credentials"),
    USER_NOT_FOUND("1002", "User Not Found");
	private String exceptionKey;
	private String message;

	@Override
	public String getExceptionKey() {
		return this.exceptionKey;
	}

	@Override
	public String getExceptionMessage() {
		return this.message;
	}

}
