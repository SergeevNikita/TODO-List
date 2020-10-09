package team.smartworld.academy.todolist.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * TodoList model
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Дата создания
     */
    private Date dateCreated;
    /**
     * Дата изменения
     */
    private Date dateChange;
    /**
     * Название списка
     */
    private String name;
    /**
     * Состояние (завершено или нет)
     */
    private boolean isDone;

    /**
     * Количество завершенных Task
     */
    private Long numberOfCompletedTask;
    /* Список дел (нужно ли?) */
    //List<Task> todoList;
}
