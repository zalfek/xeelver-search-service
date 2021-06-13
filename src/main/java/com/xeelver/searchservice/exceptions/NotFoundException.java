package com.xeelver.searchservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Data with requested parameters is not found")
public class NotFoundException extends RuntimeException{

}
