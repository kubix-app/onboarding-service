package app.kubix.onboarding.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseModel<T> {

	private String code;

	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String key;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String path;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public ResponseModel() {
	}

	public ResponseModel(String errorCode, String message) {
		this.code = errorCode;
		this.message = message;
	}

	public ResponseModel(String errorCode, String message, T result, String key, String path) {
		this.code = errorCode;
		this.message = message;
		this.data = result;
		this.key = key;
		this.path = path;
	}

	public ResponseModel(T result) {
		this.code = "200";
		this.message = "OK";
		this.data = result;
	}
}
