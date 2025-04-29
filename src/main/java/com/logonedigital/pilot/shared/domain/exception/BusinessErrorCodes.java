package com.logonedigital.pilot.shared.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
public enum BusinessErrorCodes {

    ACCOUNT_LOCKED(302, FORBIDDEN,"Account Locked"),
    ACCOUNT_DISABLED(303, FORBIDDEN,"Account Disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN,"Incorrect credentials"),
    INVALID_TOKEN(305, FORBIDDEN,"Invalid token"),
    ACCESS_DENIED(306, FORBIDDEN,"Not an ADMIN, You are not allowed to access this resource"),
    ;
    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code,  HttpStatus httpStatus,String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
