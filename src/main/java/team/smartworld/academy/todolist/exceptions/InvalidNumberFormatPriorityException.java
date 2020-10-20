package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskInvalidIdException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidNumberFormatPriorityException extends TaskListException {
    /**
     * Constructor
     */
    public InvalidNumberFormatPriorityException() {
        super("Invalid format for parameter priority: No Number", 12);
    }
}

