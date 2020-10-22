package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс исключения сообщающий о том, что обьект не найден
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends TaskListException {

    /**
     * Конструктор
     *
     * @param exceptionType принемает тип исключения
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
