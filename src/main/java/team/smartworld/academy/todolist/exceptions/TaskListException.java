package team.smartworld.academy.todolist.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Super class from other exceptions
 */
@Getter
@Setter
public class TaskListException extends Exception {

    private int codeException;
    private String message;

    /**
     * Constructor
     *
     * @param message       message for user
     * @param codeException exception code fore user
     */
    public TaskListException(String message, int codeException) {
        super();
        this.message = message;
        this.codeException = codeException;
    }
}
