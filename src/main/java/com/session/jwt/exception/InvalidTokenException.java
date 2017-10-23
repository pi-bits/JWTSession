package com.session.jwt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidTokenException extends RuntimeException {
    Logger LOGGER = LoggerFactory.getLogger(InvalidTokenException.class);
    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    public InvalidTokenException(String token_missing) {
        LOGGER.debug(token_missing);
    }
}
