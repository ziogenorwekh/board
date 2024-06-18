package com.portfolio.boardproject.exception;

import com.portfolio.boardproject.vo.ExceptionResponseVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponseVO> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserEmailDuplicatedException.class)
    public ResponseEntity<ExceptionResponseVO> handleUserEmailDuplicatedException(UserEmailDuplicatedException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponseVO> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(MailSendException.class)
    public ResponseEntity<ExceptionResponseVO> handleMailSendException(MailSendException e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponseVO> handleAlreadyRegisteredException(AlreadyRegisteredException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<ExceptionResponseVO> handleNotOwnerException(NotOwnerException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ExceptionResponseVO> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseVO> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponseVO(e.getMessage(), Date.from(Instant.now())));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        ArrayList<ExceptionResponseVO> exceptionResponseVOS = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {exceptionResponseVOS.add(new
                ExceptionResponseVO(error.getDefaultMessage(), Date.from(Instant.now())));});
        return ResponseEntity.badRequest().body(exceptionResponseVOS);
    }
}
