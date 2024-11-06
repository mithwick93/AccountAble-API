package org.mithwick93.accountable.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    /**
     * Handle {@link NotFoundException}.
     *
     * @param ex      Exception to handle.
     * @param request Web request.
     * @return RFC-7807 {@link ProblemDetail} wrapped in {@link ResponseEntity} with HTTP status 404.
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleStockNotFound(NotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not found");
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle Bad input.
     *
     * @param ex      Exception to handle.
     * @param request Web request.
     * @return RFC-7807 {@link ProblemDetail} wrapped in {@link ResponseEntity} with HTTP status 400.
     */
    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> handleConstraintViolation(Exception ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Invalid request content.");
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle invalid requests thrown with {@link MethodArgumentNotValidException }.
     *
     * @param ex      Exception to handle.
     * @param request Web request.
     * @return RFC-7807 {@link ProblemDetail} wrapped in {@link ResponseEntity} with HTTP status 400.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, errors.toString());
        problemDetail.setTitle("Invalid request content.");
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        return new ResponseEntity<>(problemDetail, headers, status);
    }
}

