package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadParameterException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParameterException extends TaskListException {

    /**
     * Constructor
     */
    public BadParameterException(ExceptionType type) {
        super(type.message, type.codeException);
    }


    public enum ExceptionType {
        NAME("name", 200),
        TITLE("title", 201),
        IS_DONE("isDone", 202),
        PRIORITY("priority", 203),
        DATE_CREATED("dateCreated", 204),
        DATE_CHANGE("dateChange", 205),
        OFFSET("offset", 206),
        LIMIT("limit", 207),
        DATE_CREATED_SORT("dateCreatedSort", 208),
        DATE_CHANGE_SORT("dateChangeSort", 209),
        NAME_SORT("nameSort", 210),
        IS_DONE_SORT("isDoneSort", 211);

        private final String message;
        private final int codeException;

        ExceptionType(String name, int ordinal) {
            message = "Parameter '" + name + "' not found or empty";
            codeException = ordinal;
        }
    }

}
