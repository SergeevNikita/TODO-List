package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskListNotFoundException class
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends TaskListException {
    /**
     * Constructor
     */
    public NotFoundException(ExceptionType exceptionType) {
        super(exceptionType.message, exceptionType.codeException);
    }


    public enum ExceptionType {
        TASK_LIST_NOT_FOUND("Task List Not Found", 1),
        TASK_NOT_FOUND("Task Not Found", 2);

        private final String message;
        private final int codeException;

        ExceptionType(String name, int ordinal) {
            message = name;
            codeException = ordinal;
        }
    }
}
