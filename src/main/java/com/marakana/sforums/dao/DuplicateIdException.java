package com.marakana.sforums.dao;

public class DuplicateIdException extends DataAccessException {

    private static final long serialVersionUID = -1695117366940545940L;

    public DuplicateIdException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DuplicateIdException(String msg) {
        super(msg);
    }

}
