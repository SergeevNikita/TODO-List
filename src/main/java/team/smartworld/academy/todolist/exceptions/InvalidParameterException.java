package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс исключения сообщающий о том, что введёный обязательный параметр имеет недопустимый формат значения
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParameterException extends TaskListException {

    /**
     * Конструктор
     *
     * @param exceptionType принемает тип исключения
     */
    public InvalidParameterException(ExceptionType exceptionType) {
        super(exceptionType.message, exceptionType.codeException);
    }

    public enum ExceptionType {
        TASK_LIST_ID("Task List Id", 100),
        TASK_ID("Task Id", 101),
        DONE("done", 102),
        PRIORITY("priority", 103),
        LIMIT("limit", 104),
        PAGE("page", 105),
        DATE_CREATED("dateCreated", 106),
        DATE_CHANGE("dateChange", 107);

        private final String message;

        private final int codeException;

        ExceptionType(String name, int ordinal) {
            message = "Invalid format for parameter '" + name + "'";
            codeException = ordinal;
        }
    }
}
