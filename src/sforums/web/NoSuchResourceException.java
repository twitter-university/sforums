package sforums.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchResourceException() {
	}

	public NoSuchResourceException(String msg) {
		super(msg);
	}

	public NoSuchResourceException(Throwable cause) {
		super(cause);
	}

	public NoSuchResourceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
