package team.smartworld.academy.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Task model
 *
 * @author Sergeev Nikita
 * @version 1.0
 */
@Entity
@Data
public class Task {

    /**
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * id списка дел к которому принадлежит (подумать как реализовать связь)
     */

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private TaskList taskList;
    /**
     * Дата создания
     */

    private LocalDateTime dateCreated;
    /**
     * Дата изменения
     */

    private LocalDateTime dateChange;
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
    private byte priority;
    /**
     * Состояние (сделано или нет)
     */

    private boolean isDone;

}
