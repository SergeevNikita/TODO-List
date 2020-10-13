package team.smartworld.academy.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskList model
 *
 * @author Sergeev Nikita
 * @version 1.0
 */

@Entity
@Data
public class TaskList {
    /**
     * ID
     */
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
     * Название списка
     */
    @NotNull
    @Size(min = 1, max = 30)
    private String name;
    /**
     * Состояние (завершено или нет)
     */
    @JsonIgnore
    private boolean isDone;

    /**
     * Количество завершенных Task
     */
    @JsonIgnore
    private Long numberOfCompletedTask;

    /* Список дел (нужно ли?) */
    @JsonIgnore
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }
}
