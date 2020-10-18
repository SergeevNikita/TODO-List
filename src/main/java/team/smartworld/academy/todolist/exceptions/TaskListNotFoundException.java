package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskListNotFoundException class
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskListNotFoundException extends TaskListException {
    /**
     * Constructor
     */
    public TaskListNotFoundException() {
        super("Task List Not Found", 1);
    }
}
