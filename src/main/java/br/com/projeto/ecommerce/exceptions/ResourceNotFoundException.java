package br.com.projeto.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -4915576740569716913L;

    public ResourceNotFoundException(String ex) {
        super(ex);
    }
}
