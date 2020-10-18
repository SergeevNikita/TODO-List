package team.smartworld.academy.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectParameterValueException extends TaskListException {
    /**
     * Constructor
     *
     * @param parameter incorrect parameter
     */
    public IncorrectParameterValueException(String parameter) {
        super("Incorrect parameter value: " + parameter, 5);
    }
}




