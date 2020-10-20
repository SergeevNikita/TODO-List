package team.smartworld.academy.todolist.exceptions;

import lombok.Getter;

/**
 * Super class from other exceptions
 */
@Getter
public class TaskListException extends Exception {

    private final int codeException;
    private final String message;

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
