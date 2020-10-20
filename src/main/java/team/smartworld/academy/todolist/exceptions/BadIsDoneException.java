package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskBadIsDoneException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadIsDoneException extends TaskListException {

    /**
     * Task List Bad Name Exception
     */
    public BadIsDoneException() {
        super("Parameter 'isDone' not found or empty", 10);
    }

}
