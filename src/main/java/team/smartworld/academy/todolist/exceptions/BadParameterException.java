package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс исключения сообщающий о том, что введёный обязательный параметр отсутствует или отсутствует его значение
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParameterException extends TaskListException {

    /**
     * Конструктор
     *
     * @param type принемает тип исключения
     */
    public BadParameterException(ExceptionType type) {
        super(type.message, type.codeException);
    }

    public enum ExceptionType {
        NAME("name", 200),
        TITLE("title", 201),
        DONE("done", 202),
        PRIORITY("priority", 203),
        DATE_CREATED("dateCreated", 204),
        DATE_CHANGE("dateChange", 205),
        PAGE("page", 206),
        LIMIT("limit", 207);

        private final String message;

        private final int codeException;

        ExceptionType(String name, int ordinal) {
            message = "Parameter '" + name + "' not found or empty";
            codeException = ordinal;
        }
    }
}
