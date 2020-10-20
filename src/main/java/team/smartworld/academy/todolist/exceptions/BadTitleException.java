package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskListBadNameException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadTitleException extends TaskListException {

    /**
     * Task List Bad Name Exception
     */
    public BadTitleException() {
        super("Parameter 'title' not found", 8);
    }

}
