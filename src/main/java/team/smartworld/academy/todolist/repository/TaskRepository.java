package team.smartworld.academy.todolist.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.smartworld.academy.todolist.entity.Task;

import java.util.UUID;

/**
 * Интерфейс репозитория для работы с БД класа Task
 */
@Repository
@Transactional
public interface TaskRepository extends CrudRepository<Task, UUID> {

}
