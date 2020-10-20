package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskInvalidIdException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTaskIdException extends TaskListException {
    /**
     * Constructor
     */
    public InvalidTaskIdException() {
        super("Invalid format for parameter Task ID", 4);
    }
}

