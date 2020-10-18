package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidParameterException extends TaskListException {
    /**
     * Constructor
     *
     * @param parameter invalid parameter
     */
    public InvalidParameterException(String parameter) {
        super("Invalid format for parameter: " + parameter, 4);
    }
}
