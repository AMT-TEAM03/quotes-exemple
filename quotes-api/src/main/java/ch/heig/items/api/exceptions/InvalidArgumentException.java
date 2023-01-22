package ch.heig.items.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException() { super ("Malformated request.");}
}