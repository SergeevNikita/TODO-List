package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskBadIsDoneException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OutOfRangePriorityException extends TaskListException {

    public static final int CODE_EXCEPTION = 300;

    /**
     * Constructor
     */
    public OutOfRangePriorityException() {
        super("Out of range priority (can be from 1 to 5)", CODE_EXCEPTION);
    }

}
