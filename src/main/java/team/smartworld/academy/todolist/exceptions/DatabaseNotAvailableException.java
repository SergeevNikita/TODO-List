package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseNotAvailableException extends TaskListException {
    /**
     * Constructor
     */
    public DatabaseNotAvailableException() {
        super("Database not available", 6);
    }
}

