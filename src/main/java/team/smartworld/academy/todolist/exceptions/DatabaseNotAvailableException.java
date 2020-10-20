package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * DatabaseNotAvailableException
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseNotAvailableException extends TaskListException {

    public static final int CODE_EXCEPTION = 500;

    /**
     * Constructor
     */
    public DatabaseNotAvailableException() {
        super("Database not available", CODE_EXCEPTION);
    }
}

