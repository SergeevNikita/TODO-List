package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskListInvalidIdException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTaskListIdException extends TaskListException {
    /**
     * Constructor
     */
    public InvalidTaskListIdException() {
        super("Invalid format for parameter Task List ID", 3);
    }
}
