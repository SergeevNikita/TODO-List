package team.smartworld.academy.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс исключения сообщающий о том, что обьект имеет недопустимое значение
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OutOfRangePriorityException extends TaskListException {

    public static final int CODE_EXCEPTION = 300;

    /**
     * Конструктор
     */
    public OutOfRangePriorityException() {
        super("Out of range priority (can be from 1 to 5)", CODE_EXCEPTION);
    }

}
