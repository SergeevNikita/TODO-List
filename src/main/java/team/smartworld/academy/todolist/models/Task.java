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
 * Task model
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * id списка дел к которому принадлежит (подумать как реализовать связь)
     */
    private Long idTodoList;
    /**
     * Дата создания
     */
    private Date dateCreate;
    /**
     * Дата изменения
     */
    private Date dateChange;
    /**
     * Название дела
     */
    private String name;
    /**
     * Краткое описание title
     */
    private String title;
    /**
     * Срочность 1-5
     */
    private byte priority;
    /**
     * Состояние (зделано или нет)
     */
    private boolean isCompleted;
}
