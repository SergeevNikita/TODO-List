package team.smartworld.academy.todolist.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.smartworld.academy.todolist.entity.Task;

import java.util.*;

/**
 * Интерфейс репозитория для работы с БД класа Task
 */
@Repository
@Transactional
public interface TaskRepository extends CrudRepository<Task, UUID>, JpaSpecificationExecutor<Task> {

    /**
     * Метод для поиска в БД обьекта Task
     *
     * @param id         принемает Task id
     * @param taskListId принемает TaskList id
     * @return возвращает Optional обьекта Task
     */
    Optional<Task> findByIdAndTaskListId(UUID id, UUID taskListId);
}
