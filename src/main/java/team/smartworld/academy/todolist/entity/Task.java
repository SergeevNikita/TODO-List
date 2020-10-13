package team.smartworld.academy.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * id списка дел к которому принадлежит (подумать как реализовать связь)
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private TaskList taskList;
    /**
     * Дата создания
     */
    @JsonIgnore
    private String dateCreated;
    /**
     * Дата изменения
     */
    @JsonIgnore
    private String dateChange;
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
    @JsonIgnore
    private boolean isDone;

}
