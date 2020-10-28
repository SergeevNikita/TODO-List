package team.smartworld.academy.todolist.specs;

import org.springframework.data.jpa.domain.Specification;
import team.smartworld.academy.todolist.entity.TaskList;

import java.time.LocalDateTime;

/**
 * Класс спецификаций для организования фильтраци при запросе к БД обьекта TaskLIst
 */
public class TaskListSpecs {

    private TaskListSpecs() {
    }

    /**
     * Мотод для осуществления фильтрации по полю 'name'
     *
     * @param name Принемает строку со значением поля 'name'
     * @return возвращает обьект класса Specification
     */
    public static Specification<TaskList> withName(String name) {
        return (root, query, cb) -> name == null ? null : cb.equal(root.get("name"), name);
    }

    /**
     * Мотод для осуществления фильтрации по полю 'dateChange'
     *
     * @param dateChange Принемает строку со значением поля 'dateChange'
     * @return возвращает обьект класса Specification
     */
    public static Specification<TaskList> withDateChange(LocalDateTime dateChange) {
        return (root, query, cb) -> dateChange == null ? null : cb.equal(root.get("dateChange"), dateChange);
    }

    /**
     * Мотод для осуществления фильтрации по полю 'dateCreated'
     *
     * @param dateCreated Принемает строку со значением поля 'dateCreated'
     * @return возвращает обьект класса Specification
     */
    public static Specification<TaskList> withDateCreated(LocalDateTime dateCreated) {
        return (root, query, cb) -> dateCreated == null ? null : cb.equal(root.get("dateCreated"), dateCreated);
    }

    /**
     * Мотод для осуществления фильтрации по полю 'done'
     *
     * @param done Принемает строку со значением поля 'done'
     * @return возвращает обьект класса Specification
     */
    public static Specification<TaskList> withDone(Boolean done) {
        return (root, query, cb) -> done == null ? null : cb.equal(root.get("done"), done);
    }
}
