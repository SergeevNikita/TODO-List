package team.smartworld.academy.todolist.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private Date dateCreated;
    /**
     * Дата изменения
     */
    private Date dateChange;
    /**
     * Название дела
     */
    @NotNull
    @Size(min = 1, max = 30)
    private String name;
    /**
     * Краткое описание title
     */
    @NotNull
    @Size(max = 255)
    private String title;
    /**
     * Срочность 1-5
     */
    @Min(1)
    @Max(5)
    private byte priority;
    /**
     * Состояние (сделано или нет)
     */
    private boolean isDone;
}
