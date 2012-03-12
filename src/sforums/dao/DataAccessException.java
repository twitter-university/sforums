package sforums.dao;

public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1693483355407503004L;

    public DataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DataAccessException(String msg) {
        super(msg);
    }
}
