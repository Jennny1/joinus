package com.project.joinus.exception;

import com.project.joinus.error.ResponseError;
import java.util.List;
import org.springframework.http.ResponseEntity;

public class EmailExistException extends RuntimeException {

  public EmailExistException(String s) {
    super(s);
  }
}
