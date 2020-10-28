package team.smartworld.academy.todolist.specs;

import org.springframework.data.jpa.domain.Specification;
import team.smartworld.academy.todolist.entity.*;

/**
 * Класс спецификаций для организования фильтраци при запросе к БД обьекта Task
 */
public class TaskSpecs {

    private TaskSpecs() {
    }

    /**
     * Мотод для осуществления фильтрации по полю 'done'
     *
     * @param done Принемает строку со значением поля 'done'
     * @return возвращает обьект класса Specification
     */
    public static Specification<Task> withDone(Boolean done) {
        return (root, query, cb) -> done == null ? null : cb.equal(root.get("done"), done);
    }

    /**
     * Мотод для осуществления фильтрации по полю 'taskList'
     *
     * @param taskList Принемает строку со значением поля 'taskList'
     * @return возвращает обьект класса Specification
     */
    public static Specification<Task> withTaskList(TaskList taskList) {
        return (root, query, cb) -> taskList == null ? null : cb.equal(root.get("taskList"), taskList);
    }
}
