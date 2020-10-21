package team.smartworld.academy.todolist.exceptions;

import lombok.Getter;

/**
 * Главный класс для других классов ошибок
 */
@Getter
public class TaskListException extends Exception {
    /**
     * Код ошибки
     */
    private final int codeException;
    /**
     * Сообщение для пользователя
     */
    private final String message;

    /**
     * Конструктор
     *
     * @param message       Сообщение для пользователя
     * @param codeException Код ошибки
     */
    public TaskListException(String message, int codeException) {
        super();
        this.message = message;
        this.codeException = codeException;
    }
}
