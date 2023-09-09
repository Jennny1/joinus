package com.project.joinus.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseError {
  private String field;
  private String message;

  public static ResponseError of(FieldError e) {
    return ResponseError.builder()
        .field(e.getField())
        .message(e.getDefaultMessage())
        .build();
  }

  public ResponseEntity<?> errorCheck (Errors errors) {

    List<ResponseError> responseErrorList = new ArrayList<>();
    if (errors.hasErrors()) {
      errors.getAllErrors().forEach((e) -> {
        responseErrorList.add(ResponseError.of((FieldError) e));
      });

      return new ResponseEntity<>(responseErrorList, HttpStatus.BAD_REQUEST);

    }

    return new ResponseEntity<>(HttpStatus.OK);

  }
}
