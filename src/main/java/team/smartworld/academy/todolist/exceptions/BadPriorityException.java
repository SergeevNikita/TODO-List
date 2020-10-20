package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskBadIsDoneException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadPriorityException extends TaskListException {

    /**
     * Task List Bad Name Exception
     */
    public BadPriorityException() {
        super("Parameter 'priority' not found or empty", 11);
    }

}
