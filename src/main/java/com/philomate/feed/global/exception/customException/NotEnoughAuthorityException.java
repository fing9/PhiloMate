package com.philomate.feed.global.exception.customException;

import lombok.Getter;

@Getter
public class NotEnoughAuthorityException extends RuntimeException {

    public NotEnoughAuthorityException(String message) {
        super(message);
    }

}
