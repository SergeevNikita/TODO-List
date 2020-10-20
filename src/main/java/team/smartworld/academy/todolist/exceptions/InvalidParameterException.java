package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParameterException extends TaskListException {
    /**
     * Constructor
     *
     * @param exceptionType Type exception
     */
    public InvalidParameterException(ExceptionType exceptionType) {
        super(exceptionType.message, exceptionType.codeException);
    }


    public enum ExceptionType {
        TASK_LIST_ID("Task List Id", 100),
        TASK_ID("Task Id", 101),
        IS_DONE("isDone", 102),
        PRIORITY("priority", 103),
        LIMIT("limit", 104),
        OFFSET("offset", 105),
        DATE_CREATED("dateCreated", 106),
        DATE_CHANGE("dateChange", 107),
        DATE_CREATED_SORT("dateCreatedSort", 108),
        DATE_CHANGE_SORT("dateChangeSort", 109),
        NAME_SORT("nameSort", 110),
        IS_DONE_SORT("isDoneSort", 111);

        private final String message;
        private final int codeException;

        ExceptionType(String name, int ordinal) {
            message = "Invalid format for parameter '" + name + "'";
            codeException = ordinal;
        }
    }
}
