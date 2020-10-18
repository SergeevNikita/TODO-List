package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TaskListBadParameterException class
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskListBadParameterException extends TaskListException {

    /**
     * @param badArgs Bad arguments list
     */
    public TaskListBadParameterException(String... badArgs) {
        super("", 3);
        StringBuilder message = new StringBuilder();
        message.append("Parameter(s):");
        for (String badArg : badArgs) {
            message.append(" '").append(badArg).append("',");
        }
        message.append(" is not found or empty");
        setMessage(message.toString());
    }

}
