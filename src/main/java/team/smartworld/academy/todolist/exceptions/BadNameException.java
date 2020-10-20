package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskListBadNameException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadNameException extends TaskListException {

    /**
     * Task List Bad Name Exception
     */
    public BadNameException() {
        super("Parameter 'name' not found or empty", 7);
    }

}
